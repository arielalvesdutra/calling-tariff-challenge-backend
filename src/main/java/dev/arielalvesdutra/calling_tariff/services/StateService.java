package dev.arielalvesdutra.calling_tariff.services;

import dev.arielalvesdutra.calling_tariff.entities.State;
import dev.arielalvesdutra.calling_tariff.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public State create(State state) {
        return stateRepository.save(state);
    }

    public List<State> findAll() {
        return stateRepository.findAll();
    }

    public State findById(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() ->
                        new EntityNotFoundException("State with id " + stateId + " not found!"));
    }

    public State findByAbbreviation(String abbreviation) {
        State state = stateRepository.findByAbbreviation(abbreviation);

        if (state == null) {
            throw new EntityNotFoundException(
                    "State with abbreviation " + abbreviation+ " not found!");
        }

        return state;
    }

    @Transactional
    public void deleteAll() {
        stateRepository.deleteAll();
    }
}
