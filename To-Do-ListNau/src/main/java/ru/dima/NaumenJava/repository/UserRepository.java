package ru.dima.naumenjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dima.naumenjava.entity.User;


@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
}
