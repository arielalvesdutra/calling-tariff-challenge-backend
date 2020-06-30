package dev.arielalvesdutra.calling_tariff.services;


import dev.arielalvesdutra.calling_tariff.controllers.dtos.CalculateCallRecordRequestDTO;
import dev.arielalvesdutra.calling_tariff.entities.*;
import dev.arielalvesdutra.calling_tariff.exceptions.CallingTariffException;
import dev.arielalvesdutra.calling_tariff.repositories.CallRecordRepository;
import dev.arielalvesdutra.calling_tariff.services.dtos.CalculateCallRecordDTO;
import dev.arielalvesdutra.calling_tariff.services.dtos.CreateCallRecordDTO;
import dev.arielalvesdutra.calling_tariff.services.dtos.UpdateUserCallPlanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static dev.arielalvesdutra.calling_tariff.helpers.BigDecimalHelper.percentage;
import static dev.arielalvesdutra.calling_tariff.helpers.ObjectHelper.isEmpty;

/**
 * Service of call records.
 */
@Service
public class CallRecordService {

    private final String USER_RUNNER_EMAIL = "runner@teste.com";

    @Autowired
    private UserService userService;

    @Autowired
    private DDDService dddService;

    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @Autowired
    private CallTariffMapService callTariffMapService;

    @Autowired
    private CallRecordRepository callRecordRepository;

    public CallRecordService(
            CallRecordRepository callRecordRepository,
            DDDService dddService,
            SystemConfigurationService systemConfigurationService,
            CallTariffMapService callTariffMapService,
            UserService userService) {

        this.callRecordRepository = callRecordRepository;
        this.dddService = dddService;
        this.systemConfigurationService = systemConfigurationService;
        this.callTariffMapService = callTariffMapService;
        this.userService = userService;
    }

    /**
     * Create and persist a CallRecord on database.
     *
     * @param createDto Data Access Object for create a Call record
     *
     * @return return the created Call record
     */
    public CallRecord create(CreateCallRecordDTO createDto) {
        CalculateCallRecordDTO calculateDto = new CalculateCallRecordDTO();

        calculateDto.setClient(createDto.getClient());
        calculateDto.setDuration(createDto.getMinutes());

        try {
            DDD origin = dddService.findFirstByCode(createDto.getOriginCode());
            DDD destination = dddService.findFirstByCode(createDto.getDestinationCode());
            calculateDto
                    .setOrigin(origin)
                    .setDestination(destination);

        } catch (EntityNotFoundException ignored) { }

        CallRecord callRecord = calculate(calculateDto);

        return callRecordRepository.save(callRecord);
    }

    /**
     * Calculate price and create a CallRecord object.
     *
     * The record IS NOT stored in the database.
     *
     * @param calculateDto Data Access Object for calculating a Call
     *
     * @return return a CallRecord with calculated price
     */
    public CallRecord calculate(CalculateCallRecordDTO calculateDto) {
        if (calculateDto == null) {
            throw new CallingTariffException("Invalid calculation parameter!");
        }

        User client = calculateDto.getClient();
        Integer durationInMinutes = calculateDto.getDuration();
        DDD origin = calculateDto.getOrigin();
        DDD destination = calculateDto.getDestination();
        
        BigDecimal minuteCost = findCallMinuteCost(origin, destination);
        BigDecimal calculatedPrice = calculatePrice(minuteCost, durationInMinutes, client);

        return new CallRecord()
                .setClient(client)
                .setMinutes(durationInMinutes)
                .setPrice(calculatedPrice);
    }

    /**
     * Temporary method.
     *
     *
     * @param dto DTO received from controller request
     * @return
     */
    public CalculateCallRecordDTO fillCalculateCallRecordDTO(CalculateCallRecordRequestDTO dto) {
        User user = userService.findByEmail(USER_RUNNER_EMAIL);
        UpdateUserCallPlanDTO updateUserCallPlanDTO = new UpdateUserCallPlanDTO()
                .setUserUuid(user.getUuid());

        if (!isEmpty(dto.getCallPlanUuid())) {
            updateUserCallPlanDTO.setCallPlanUuid(dto.getCallPlanUuid());
        }

        userService.updateUserCallPlan(updateUserCallPlanDTO);

        CalculateCallRecordDTO calculateDto = new CalculateCallRecordDTO()
                .setClient(user)
                .setDuration(dto.getDuration());

        if (!isEmpty(dto.getOriginCode()) && !isEmpty(dto.getDestinationCode())) {
            DDD origin = dddService.findFirstByCode(dto.getOriginCode());
            DDD destination = dddService.findFirstByCode(dto.getDestinationCode());

            calculateDto
                    .setOrigin(origin)
                    .setDestination(destination);
        }

        return calculateDto;
    }


    /**
     * Calculate the price of a call.
     *
     * @param minuteCost Cost of each minute
     * @param minutes Minutes of the call
     * @param user User that made the call
     *
     * @return Calculated price
     */
    private BigDecimal calculatePrice(BigDecimal minuteCost, Integer minutes, User user) {
        if (user.hasACallPlan()) {
            return calculatePriceWithCallPlan(user, minuteCost, minutes);
        }

        return calculatePriceWithoutCallPlan(minuteCost, minutes);
    }

    /**
     * Method for calculating the price of a call when the user
     * does not have a linked calling plan.
     *
     * @param minuteCost  Cost of each minute
     * @param minutes Minutes of the call
     *
     * @return Calculated price
     */
    private BigDecimal calculatePriceWithoutCallPlan(BigDecimal minuteCost, Integer minutes) {
        return minuteCost.multiply(new BigDecimal(minutes));
    }

    /**
     * Method for calculating the price of a call when the user
     * does have a linked calling plan.
     *
     * If the user has not consumed all the minutes of the plan,
     * the cost of the call is ZERO.
     *
     * @param user User that made the call
     * @param minuteCost Cost of each minute
     * @param currentCallMinutes Minutes of the current call
     *
     * @return Calculated price
     */
    private BigDecimal calculatePriceWithCallPlan(User user, BigDecimal minuteCost, Integer currentCallMinutes) {
        CallPlan callPlan = user.getCallPlan();
        Integer monthConsumedMinutes = findTheCurrentConsumptionOfMinutesOfTheMonth(user);
        Integer totalConsumedMinutes = monthConsumedMinutes + currentCallMinutes;
        Integer callPlanMinutes = callPlan.getMinutes();

        if (isConsumptionNotExceeded(totalConsumedMinutes, callPlanMinutes)) {
            return BigDecimal.ZERO;
        }

        return calculatePriceWithExceededCallPlan(
                totalConsumedMinutes, currentCallMinutes, callPlanMinutes, minuteCost);
    }

    /**
     * Calculate the price of a Call when the client has consumed all of the minutes
     * of the Call Plan.
     *
     * The minutes cost is increased by a percentage that is pre-configured in the
     * configurations of the system.
     *
     * @param totalConsumedMinutes Total of consumed minutes in the month
     * @param currentCallMinutes Current call minutes
     * @param callPlanMinutes Minutes that the calling plan allow to be
     *                        consumed in the month without extra charges
     * @param minuteCost Cost of each minute
     *
     * @return
     */
    private BigDecimal calculatePriceWithExceededCallPlan(
            Integer totalConsumedMinutes,
            Integer currentCallMinutes,
            Integer callPlanMinutes,
            BigDecimal minuteCost) {

        BigDecimal feePercentage = findSystemConfigurationExceedingPlanFee();
        BigDecimal minuteCostWithPercentage = minuteCost.add(percentage(minuteCost, feePercentage));

        if ((callPlanMinutes + currentCallMinutes) < totalConsumedMinutes) {
            return minuteCostWithPercentage.multiply(new BigDecimal(currentCallMinutes));
        }

        return minuteCostWithPercentage.multiply(new BigDecimal(totalConsumedMinutes - callPlanMinutes));
    }

    /**
     * Return true if the total consumed minutes is not higher than the minutes of the plan.
     *
     * @param totalSpentMinutes Total consumed minutes
     * @param callPlanMinutes Minutes of the plan
     * @return
     */
    private boolean isConsumptionNotExceeded(Integer totalSpentMinutes, Integer callPlanMinutes) {
        return totalSpentMinutes <= callPlanMinutes;
    }

    /**
     * Find the total of minutes that the user has consumed in the current month.
     *
     * @param user
     *
     * @return Sum of consumed minutes in the current month
     */
    private Integer findTheCurrentConsumptionOfMinutesOfTheMonth(User user) {
        List<CallRecord> callRecordList = callRecordRepository.findAllByUserIdAndCurrentMonth(user.getId());
        Integer totalConsumedMinutes = 0;

        for (CallRecord callRecord : callRecordList) {
            totalConsumedMinutes += callRecord.getMinutes();
        }

        return totalConsumedMinutes;
    }

    /**
     * Find the correspondent map of origin and destination of a call.
     *
     * @param origin DDD of origin
     * @param destination DDD of destination
     *
     * @return Call tariff map
     *
     * @throws EntityNotFoundException If there is no map for the current origin and destination
     */
    private BigDecimal findCallMinuteCost(DDD origin, DDD destination) {

        if (isEmpty(origin) || isEmpty(destination)) {
            return findSystemConfigurationDefaultCallMinuteCharge();
        }

        CallTariffMap callTariffMap =
                callTariffMapService.findByOriginCodeAndDestinationCode(
                        origin.getCode(), destination.getCode());

        return callTariffMap.getPricePerMinute();
    }

    /**
     * Find the system configuration with the default charge that is applied
     * for each minute of a call.
     *
     * This method should be used when there is no map of origin and destination of the
     * cost of each call minute.
     *
     * @return Cost of minute
     */
    private BigDecimal findSystemConfigurationDefaultCallMinuteCharge() {
        try {
            SystemConfiguration costConfig = systemConfigurationService.findDefaultCallMinuteCharge();

            return new BigDecimal(costConfig.getValue());
        } catch (EntityNotFoundException e) {
            throw new CallingTariffException("Default call minute charge must be configured!");
        }
    }

    /**
     * Find the system configuration that has a fee value that will be used
     * to charge the exceeding of a call plan.
     *
     * @return Fee percentage
     */
    private BigDecimal findSystemConfigurationExceedingPlanFee() {
        try {
            SystemConfiguration feeConfig = systemConfigurationService.findExceedingPlanFee();

            return new BigDecimal(feeConfig.getValue());
        } catch (EntityNotFoundException e) {
            throw new CallingTariffException("Default call charge must be configured!");
        }
    }

    @Transactional
    public void deleteAll() {
        callRecordRepository.deleteAll();
    }
}
