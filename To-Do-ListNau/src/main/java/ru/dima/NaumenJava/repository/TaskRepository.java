package ru.dima.naumenjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dima.naumenjava.entity.Task;

import java.util.List;


@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrderByPriorityAsc();

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    List<Task> findByUserId(Long userId);
}
