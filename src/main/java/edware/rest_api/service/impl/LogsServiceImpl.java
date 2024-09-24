package edware.rest_api.service.impl;

import edware.rest_api.domain.model.Logs;
import edware.rest_api.domain.repository.LogsRepository;
import edware.rest_api.service.LogsService;
import edware.rest_api.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogsServiceImpl implements LogsService {

    private final LogsRepository logsRepository;

    public LogsServiceImpl(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Logs> findAll() {
        return this.logsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Logs findById(Long aLong) {
        return this.logsRepository.findById(aLong).orElseThrow(() -> new NotFoundException("Log not found."));
    }

    @Override
    @Transactional
    public Logs create(Logs entity) {
        return this.logsRepository.save(entity);
    }

    @Override
    @Transactional
    public Logs update(Long id, Logs entity) {
        Logs dbLogs = this.findById(id);

        if (!dbLogs.getId().equals(entity.getId())) {
            throw new IllegalArgumentException("Update IDs must be the same.");
        }
        dbLogs.setActivity(entity.getActivity());
        dbLogs.setRunner(entity.getRunner());
        dbLogs.setLocation(entity.getLocation());
        dbLogs.setActivityDate(entity.getActivityDate());
        dbLogs.setActivityTime(entity.getActivityTime());
        dbLogs.setDistance(entity.getDistance());

        return this.logsRepository.save(dbLogs);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Logs dbLogs = this.findById(id);
        this.logsRepository.delete(dbLogs);
    }
}
