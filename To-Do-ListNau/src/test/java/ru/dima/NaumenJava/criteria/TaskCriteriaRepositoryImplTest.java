package ru.dima.naumenjava.criteria;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.naumenjava.entity.Category;
import ru.dima.naumenjava.entity.Status;
import ru.dima.naumenjava.entity.Task;
import ru.dima.naumenjava.entity.User;
import ru.dima.naumenjava.repository.CategoryRepository;
import ru.dima.naumenjava.repository.StatusRepository;
import ru.dima.naumenjava.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = NONE)
@Import(TaskCriteriaRepositoryImpl.class)
class TaskCriteriaRepositoryImplTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TaskCriteriaRepositoryImpl taskCriteriaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StatusRepository statusRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("dima", "0708", "dima.dima@gmail.com");
        userRepository.save(user);

        Category category = new Category("test1", "test1");
        categoryRepository.save(category);

        Status status = new Status("test1");
        statusRepository.save(status);

        Status status2 = new Status("test2");
        statusRepository.save(status2);

        Task task = new Task(1l, "proverka", "test1", LocalDate.now());
        task.setCategory(category);
        task.setStatus(status);
        task.setUser(user);
        entityManager.persist(task);

        Task task2 = new Task(2l, "proverka2", "test2", LocalDate.now());
        task2.setStatus(status2);
        task2.setUser(user);
        task2.setCategory(category);
        testEntityManager.persist(task2);
    }

    @Test
    public void testFindByStatusAndPriority() {
        List<Task> result = taskCriteriaRepository.findByStatusAndPriority("test1", 1l);

        assertEquals(1, result.size());
        assertEquals("test1", result.get(0).getStatus().getName());
        assertEquals(1, result.get(0).getPriority());
    }

    @Test
    public void testFindTasksByUserId() {
        List<Task> result = taskCriteriaRepository.findTasksByUserId(user.getId());

        assertEquals(2, result.size());
        assertEquals(user.getId(), result.get(0).getUser().getId());
        assertEquals(user.getId(), result.get(1).getUser().getId());
    }
}
