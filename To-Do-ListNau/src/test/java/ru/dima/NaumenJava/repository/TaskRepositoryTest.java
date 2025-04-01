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
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

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

        Task task = new Task(1l, "proverka", "test1", LocalDate.now());
        task.setCategory(category);
        task.setStatus(status);
        task.setUser(user);
        taskRepository.save(task);
    }

    @Test
    void testFindAllByOrderByPriorityAsc() {
        List<Task> tasks = taskRepository.findAllByOrderByPriorityAsc();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("proverka", tasks.getFirst().getDescription());
    }

    @Test
    void testFindByUserId() {
        List<Task> tasks = taskRepository.findByUserId(user.getId());

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(user.getId(), tasks.get(0).getUser().getId());
    }

    @Test
    void testFindByUserIdWhenNoTasksExist() {
        User anotherUser = new User();
        anotherUser.setUserName("anotheruser");
        anotherUser.setEmail("anotheruser@example.com");
        anotherUser.setPassword("1234");
        userRepository.save(anotherUser);

        List<Task> tasks = taskRepository.findByUserId(anotherUser.getId());

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }
}