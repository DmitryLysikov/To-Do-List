package ru.dima.naumenjava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.naumenjava.entity.Category;
import ru.dima.naumenjava.entity.Status;
import ru.dima.naumenjava.entity.Task;
import ru.dima.naumenjava.entity.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = NONE)
public class CrudRepositoryTaskTest {

    @Autowired
    private CrudRepositoryTask crudRepositoryTask;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StatusRepository statusRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        LocalDate today = LocalDate.now();

        Status status1 = new Status();
        status1.setName("In Progress");
        statusRepository.save(status1);

        Category category = new Category();
        category.setName("Category Name");
        category.setDescription("Category Description");
        categoryRepository.save(category);

        testUser = new User();
        testUser.setUserName("testuser");
        testUser.setPassword("password");
        testUser.setEmail("testuser@example.com");
        userRepository.save(testUser);

        Task task1 = new Task();
        task1.setTaskName("task1");
        task1.setDescription("Task 1");
        task1.setPriority(1l);
        task1.setUser(testUser);
        task1.setCategory(category);
        task1.setDeadlines(today);
        task1.setStatus(status1);

        Task task2 = new Task();
        task2.setTaskName("task2");
        task2.setDescription("Task 2");
        task2.setPriority(2l);
        task2.setUser(testUser);
        task2.setCategory(category);
        task2.setDeadlines(today);
        task2.setStatus(status1);

        Task task3 = new Task();
        task3.setTaskName("task3");
        task3.setDescription("Task 3");
        task3.setPriority(3l);
        task3.setUser(testUser);
        task3.setCategory(category);
        task3.setDeadlines(today);
        task3.setStatus(status1);

        crudRepositoryTask.save(task1);
        crudRepositoryTask.save(task2);
        crudRepositoryTask.save(task3);
    }

    @Test
    void testFindAllByOrderByPriorityAsc() {
        List<Task> tasks = crudRepositoryTask.findAllByOrderByPriorityAsc();

        assertNotNull(tasks);
        assertEquals(3, tasks.size());
        assertEquals("Task 1", tasks.get(0).getDescription());
        assertEquals("Task 2", tasks.get(1).getDescription());
        assertEquals("Task 3", tasks.get(2).getDescription());
    }

    @Test
    void testFindByUserId() {
        List<Task> tasks = crudRepositoryTask.findByUserId(testUser.getId());

        assertNotNull(tasks);
        assertEquals(3, tasks.size());
        assertEquals(testUser.getId(), tasks.get(0).getUser().getId());
        assertEquals(testUser.getId(), tasks.get(1).getUser().getId());
        assertEquals(testUser.getId(), tasks.get(2).getUser().getId());
    }

    @Test
    void testFindByUserIdWhenNoTasksExist() {
        User anotherUser = new User();
        anotherUser.setUserName("anotheruser");
        anotherUser.setEmail("anotheruser@example.com");
        anotherUser.setPassword("1234");
        userRepository.save(anotherUser);

        List<Task> tasks = crudRepositoryTask.findByUserId(anotherUser.getId());

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }
}