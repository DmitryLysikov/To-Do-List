package ru.dima.naumenjava.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import ru.dima.naumenjava.entity.Task;

import java.util.List;

@Service
public class TaskCriteriaRepositoryImpl implements TaskCriteriaRepository {

    private final EntityManager entityManager;

    public TaskCriteriaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Task> findByStatusAndPriority(String status, Long priority) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);

        Predicate statusPredicate = builder.equal(root.get("status").get("name"), status);
        Predicate priorityPredicate = builder.equal(root.get("priority"), priority);

        criteria.select(root).where(statusPredicate).where(priorityPredicate);

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<Task> findTasksByUserId(Long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> task = query.from(Task.class);

        Join<Object, Object> userJoin = task.join("user");
        Predicate predicate = builder.equal(userJoin.get("id"), userId);

        query.select(task).where(predicate);

        return entityManager.createQuery(query).getResultList();
    }
}
