package ru.dima.naumenjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dima.naumenjava.entity.Category;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
