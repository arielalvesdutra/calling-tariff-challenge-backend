package dev.arielalvesdutra.calling_tariff.repositories;

import dev.arielalvesdutra.calling_tariff.entities.CallRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallRecordRepository extends JpaRepository<CallRecord, Long> {

    /**
     * Find all call records by user id and current month.
     *
     * @param userId Id of the user
     *
     * @return List of call records
     */
    @Query("SELECT cr FROM CallRecord cr WHERE month(cr.createdAt) = month(now()) AND" +
            " ?1 = cr.client.id")
    List<CallRecord> findAllByUserIdAndCurrentMonth(Long userId);
}
