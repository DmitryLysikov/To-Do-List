package ru.dima.naumenjava.criteria;

import org.springframework.stereotype.Repository;
import ru.dima.naumenjava.entity.Task;

import java.util.List;

@Repository
public interface TaskCriteriaRepository {
    List<Task> findByStatusAndPriority(String status, Long priority);
    List<Task> findTasksByUserId(Long userId);
}
