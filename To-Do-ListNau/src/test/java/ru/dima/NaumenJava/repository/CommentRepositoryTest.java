package ru.dima.naumenjava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    private TaskRepository taskRepository;

    @Autowired
    private CrudRepository categoryRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    private Task task;

    @BeforeEach
    public void setUp() {
        Category category = new Category("Category Name", "Category Description");
        categoryRepository.save(category);

        Status status = new Status("Status Name");
        statusRepository.save(status);

        User user = new User("User Name", "Password", "email@email.com");
        userRepository.save(user);

        task = new Task(1l, "proverka", "test1", LocalDate.now());
        task.setCategory(category);
        task.setStatus(status);
        task.setUser(user);
        taskRepository.save(task);

        Comment comment1 = new Comment("First comment", task);
        comment1.setUser(user);
        commentRepository.save(comment1);

        Comment comment2 = new Comment("Second comment", task);
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
