package dev.arielalvesdutra.calling_tariff.services;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.repositories.DDDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class DDDService {

    @Autowired
    private DDDRepository dddRepository;

    @Transactional
    public DDD create(DDD ddd) {
        return dddRepository.save(ddd);
    }

    public List<DDD> findAll() {
        return dddRepository.findAll();
    }

    public DDD findById(Long dddId) {
        return dddRepository.findById(dddId)
                .orElseThrow(() ->
                        new EntityNotFoundException("DDD with id " + dddId + " not found!"));
    }

    public DDD findFirstByCode(Integer code) {
        DDD fetchedDDD = dddRepository.findByCode(code);

        if (fetchedDDD == null) {
            throw new EntityNotFoundException("DDD with code " + code + " not found!");
        }

        return fetchedDDD;
    }

    @Transactional
    public void deleteById(Long id) {
        DDD ddd = findById(id);
        dddRepository.deleteById(ddd.getId());
    }

    @Transactional
    public void deleteAll() {
        dddRepository.deleteAll();
    }
}
