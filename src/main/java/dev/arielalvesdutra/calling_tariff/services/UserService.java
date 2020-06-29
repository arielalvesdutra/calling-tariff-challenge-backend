package dev.arielalvesdutra.calling_tariff.services;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.entities.User;
import dev.arielalvesdutra.calling_tariff.exceptions.CallingTariffException;
import dev.arielalvesdutra.calling_tariff.repositories.UserRepository;
import dev.arielalvesdutra.calling_tariff.services.dtos.UpdateUserCallPlanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static dev.arielalvesdutra.calling_tariff.helpers.ObjectHelper.isEmpty;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CallPlanService callPlanService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(
            UserRepository userRepository,
            CallPlanService callPlanService) {

        this.userRepository = userRepository;
        this.callPlanService = callPlanService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User with id " + userId + " not found!"));
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new EntityNotFoundException("User with email " + email + " not found!");
        }

        return user;
    }

    public User findByUuid(UUID uuid) {
        User user = userRepository.findByUuid(uuid);

        if (user == null) {
            throw new EntityNotFoundException("User with uuid " + uuid + " not found!");
        }

        return user;
    }

    @Transactional
    public User create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long userId) {
        User user = findById(userId);
        userRepository.deleteById(user.getId());
    }

    @Transactional
    public User update(User parameterUser) {
        User existingUser = findById(parameterUser.getId());
        String newPassword = bCryptPasswordEncoder.encode(parameterUser.getPassword());

        existingUser.setName(parameterUser.getName());
        existingUser.setActive(parameterUser.isActive());
        existingUser.setPassword(newPassword);
        existingUser.setUpdatedAt(OffsetDateTime.now());

        return existingUser;
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }

    /**
     * Update user call plan.
     *
     * Case the plan UUID is empty, the call plan of the user is set to null.
     *
     * @param dto
     */
    @Transactional
    public CallPlan updateUserCallPlan(UpdateUserCallPlanDTO dto) {
        if (isEmpty(dto) || isEmpty(dto.getUserUuid())) {
            throw new CallingTariffException("Invalid update user call plan parameter!");
        }

        User user = findByUuid(dto.getUserUuid());

        if (isEmpty(dto.getCallPlanUuid())) {
            user.setCallPlan(null);
            return null;
        }

        CallPlan callPlan = callPlanService.findByUuid(dto.getCallPlanUuid());
        user.setCallPlan(callPlan);
        return callPlan;
    }
}
