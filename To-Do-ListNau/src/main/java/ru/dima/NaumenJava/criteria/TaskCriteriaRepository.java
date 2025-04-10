package ru.dima.naumenjava.criteria;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dima.naumenjava.entity.Task;

import java.util.List;

@RepositoryRestResource
public interface TaskCriteriaRepository {
    List<Task> findByStatusAndPriority(String status, Long priority);
    List<Task> findTasksByUserId(Long userId);
}
