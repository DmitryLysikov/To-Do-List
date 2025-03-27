package ru.dima.naumenjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dima.naumenjava.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByTaskId(Long taskId);
}