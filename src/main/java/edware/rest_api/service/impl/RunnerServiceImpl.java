package edware.rest_api.service.impl;

import edware.rest_api.domain.model.Runner;
import edware.rest_api.domain.repository.RunnerRepository;
import edware.rest_api.service.RunnerService;
import edware.rest_api.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RunnerServiceImpl implements RunnerService {

    private final RunnerRepository runnerRepository;

    public RunnerServiceImpl(RunnerRepository runnerRepository) {
        this.runnerRepository = runnerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Runner> findAll() {
        return this.runnerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Runner findById(Long id) {
        return this.runnerRepository.findById(id).orElseThrow(() -> new NotFoundException("Runner not found."));
    }

    @Override
    @Transactional
    public Runner create(Runner entity) {
        return this.runnerRepository.save(entity);
    }

    @Override
    @Transactional
    public Runner update(Long id, Runner entity) {
        Runner dbUser = this.findById(id);

        if (!dbUser.getId().equals(entity.getId())) {
            throw new IllegalArgumentException("Update IDs must be the same.");
        }

        dbUser.setName(entity.getName());
        dbUser.setEmail(entity.getEmail());
        dbUser.setAge(entity.getAge());

        return this.runnerRepository.save(dbUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Runner dbUser = this.findById(id);
        this.runnerRepository.delete(dbUser);
    }
}
