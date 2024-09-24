package edware.rest_api.service.impl;

import edware.rest_api.domain.model.Activity;
import edware.rest_api.domain.repository.ActivityRepository;
import edware.rest_api.service.ActivityService;
import edware.rest_api.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> findAll() {
        return this.activityRepository.findAll();
    }

    @Override
    public Activity findById(Long id) {
        return this.activityRepository.findById(id).orElseThrow(() -> new NotFoundException("Activity not found."));
    }

    @Override
    @Transactional
    public Activity create(Activity entity) {
        return this.activityRepository.save(entity);
    }

    @Override
    public Activity update(Long id, Activity entity) {
        Activity dbActivity = this.findById(id);

        if (!dbActivity.getId().equals(entity.getId())) {
            throw new IllegalArgumentException("Update IDs must be the same.");
        }

        dbActivity.setName(entity.getName());

        return this.activityRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Activity dbActivity = this.findById(id);
        this.activityRepository.delete(dbActivity);
    }
}
