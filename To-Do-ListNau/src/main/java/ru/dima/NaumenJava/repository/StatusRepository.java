package ru.dima.naumenjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dima.naumenjava.entity.Status;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
}
