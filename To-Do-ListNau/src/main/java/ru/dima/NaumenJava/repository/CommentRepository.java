package ru.dima.naumenjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dima.naumenjava.entity.Comment;

import java.util.List;


@RepositoryRestResource
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByTaskId(Long taskId);
}