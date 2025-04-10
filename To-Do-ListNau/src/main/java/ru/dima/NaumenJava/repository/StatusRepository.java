package ru.dima.naumenjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dima.naumenjava.entity.Status;


@RepositoryRestResource
public interface StatusRepository extends JpaRepository<Status, Long> {
}
