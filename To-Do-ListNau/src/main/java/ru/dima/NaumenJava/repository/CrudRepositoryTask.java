package ru.dima.naumenjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dima.naumenjava.entity.Task;

import java.util.List;

@Repository
public interface CrudRepositoryTask extends CrudRepository<Task, Long>{
    List<Task> findAllByOrderByPriorityAsc();

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    List<Task> findByUserId(Long userId);
}
