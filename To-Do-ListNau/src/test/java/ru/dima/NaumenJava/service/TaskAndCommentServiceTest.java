package ru.dima.naumenjava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.naumenjava.entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ru.dima.naumenjava.repository.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = NONE)
class TaskAndCommentServiceTest {

    @Autowired
    private TaskAndCommentService taskAndCommentService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StatusRepository statusRepository;

    private User testUser;
    private Category testCategory;
    private Status testStatus;

    @BeforeEach
    void setUp() {

        testUser = new User("dima", "0708", "dima.dima@gmail.com");
        userRepository.save(testUser);


        testCategory = new Category("test1", "test1");
        categoryRepository.save(testCategory);


        testStatus = new Status("test1");
        statusRepository.save(testStatus);
    }

    @Test
    void testCreateTaskWithComments() {
        String taskName = "New Task";
        String description = "Task Description";
        LocalDate deadline = LocalDate.now();
        Long priority = 1L;
        List<String> comments = List.of("First comment", "Second comment");


        Task savedTask = taskAndCommentService.createTaskWithComments(
                testUser.getId(),
                testCategory.getId(),
                testStatus.getId(),
                taskName,
                description,
                priority,
                deadline,
                comments
        );


        Optional<Task> taskOptional = taskRepository.findById(savedTask.getId());
        assertThat(taskOptional).isPresent();
        Task retrievedTask = taskOptional.get();

        assertThat(retrievedTask.getTaskName()).isEqualTo(taskName);
        assertThat(retrievedTask.getDescription()).isEqualTo(description);
        assertThat(retrievedTask.getDeadlines()).isEqualTo(deadline);
        assertThat(retrievedTask.getUser()).isEqualTo(testUser);
        assertThat(retrievedTask.getCategory()).isEqualTo(testCategory);
        assertThat(retrievedTask.getStatus()).isEqualTo(testStatus);


        List<Comment> retrievedComments = (List<Comment>) commentRepository.findAll();
        assertThat(retrievedComments).hasSize(2);
        assertThat(retrievedComments).extracting(Comment::getText)
                .containsExactlyInAnyOrder("First comment", "Second comment");

        for (Comment comment : retrievedComments) {
            assertThat(comment.getTask()).isEqualTo(retrievedTask);
            assertThat(comment.getUser()).isEqualTo(testUser);
        }
    }
}
