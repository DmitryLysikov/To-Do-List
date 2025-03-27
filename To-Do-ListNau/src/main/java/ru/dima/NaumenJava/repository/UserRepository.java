package ru.dima.naumenjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dima.naumenjava.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
