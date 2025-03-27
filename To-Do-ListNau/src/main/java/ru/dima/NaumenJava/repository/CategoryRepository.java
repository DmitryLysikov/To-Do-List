package ru.dima.naumenjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dima.naumenjava.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
