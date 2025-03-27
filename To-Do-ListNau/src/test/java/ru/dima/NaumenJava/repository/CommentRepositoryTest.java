package ru.dima.naumenjava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.naumenjava.entity.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = NONE)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CrudRepositoryTask taskRepository;

    @Autowired
    private CrudRepository categoryRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    private Task task;

    @BeforeEach
    public void setUp() {
        LocalDate today = LocalDate.now();
        Category category = new Category();
        category.setName("Category Name");
        categoryRepository.save(category);

        Status status = new Status();
        status.setName("Status Name");
        statusRepository.save(status);

        User user = new User();
        user.setUserName("User Name");
        user.setPassword("Password");
        user.setEmail("email@email.com");
        userRepository.save(user);

        task = new Task();
        task.setTaskName("Test Task");
        task.setCategory(category);
        task.setDeadlines(today);
        task.setPriority(1l);
        task.setStatus(status);
        task.setUser(user);
        taskRepository.save(task);

        Comment comment1 = new Comment();
        comment1.setText("First comment");
        comment1.setTask(task);
        comment1.setUser(user);
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setText("Second comment");
        comment2.setTask(task);
        comment2.setUser(user);
        commentRepository.save(comment2);
    }

    @Test
    void testFindByTaskId() {
        List<Comment> comments = commentRepository.findByTaskId(task.getId());

        assertNotNull(comments);
        assertEquals(2, comments.size());
        assertEquals("First comment", comments.get(0).getText());
        assertEquals("Second comment", comments.get(1).getText());
    }
}
