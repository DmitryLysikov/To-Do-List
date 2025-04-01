package ru.dima.naumenjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dima.naumenjava.entity.User;


@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
}
