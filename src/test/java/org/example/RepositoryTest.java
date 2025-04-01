package org.example;

import org.example.exceptions.TaskNameDuplicateException;
import org.example.data.Status;
import org.example.data.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class RepositoryTest {
    Repository myRepository;

    @BeforeEach
    void addSomeTasks() {
        myRepository = new Repository();
        myRepository.addTask("Уборка", "Не забудь убраться", LocalDate.parse("2004-12-12"), Status.TODO);
        myRepository.addTask("Выучи Java", "Стань супер разработчиком", LocalDate.parse("2020-10-12"), Status.IN_PROGRESS);
        myRepository.addTask("Пройди Stream API", "Закончи step по Stream API в Java", LocalDate.parse("2014-11-11"), Status.DONE);
    }

    @Test
    void findStatusByNameTest() {
        String name = "in progress";
        Repository.findStatusByName(name);
        Assertions.assertEquals(Status.IN_PROGRESS, Repository.findStatusByName(name));
    }

    @Test
    void findTaskByNameTest() {
        Assertions.assertEquals("Уборка", myRepository.findTaskByName("Уборка").getName());
    }

    @Test
    void addTaskTest() {
        myRepository.addTask("Новое дело", "Описание нового дела", LocalDate.parse("2021-10-12"), Status.TODO);
        Assertions.assertEquals("Новое дело", myRepository.findTaskByName("Новое дело").getName());
    }

    @Test
    void changeTaskTest() {
        myRepository.changeTask(myRepository.findTaskByName("Уборка"), Status.DONE, "status");
        Assertions.assertEquals(Status.DONE, myRepository.findTaskByName("Уборка").getTaskStatus());
    }

    @Test
    void deleteTaskTest() {
        myRepository.deleteTask(myRepository.findTaskByName("Уборка"));
        Assertions.assertThrows(NoSuchElementException.class, () -> myRepository.findTaskByName("Уборка"));
    }

    @Test
    void filterTasksByStatusTest() {
        Status status = Status.DONE;
        myRepository.deleteTask(myRepository.findTaskByName("Пройди Stream API"));
        List<Task> listOfTasks = Arrays.asList();
        Assertions.assertEquals(listOfTasks, myRepository.filterTasksByStatus(Status.DONE));
    }

    @Test
    void sortTasksTest() {
        myRepository.deleteTask(myRepository.findTaskByName("Пройди Stream API"));
        myRepository.deleteTask(myRepository.findTaskByName("Уборка"));
        myRepository.deleteTask(myRepository.findTaskByName("Выучи Java"));

        Task task1 = new Task("1", "1", LocalDate.parse("2020-10-12"), Status.DONE);
        Task task2 = new Task("2", "2", LocalDate.parse("2021-10-12"), Status.TODO);
        List<Task> listOfStacks = Arrays.asList(task1, task2);
        myRepository.addTaskbyTask(task2);
        myRepository.addTaskbyTask(task1);

        Assertions.assertEquals(listOfStacks, myRepository.sortTasks());
    }

    @Test
    void taskNameDublicateTest() {
        Assertions.assertThrows(TaskNameDuplicateException.class, () -> myRepository.addTask("Уборка", "Опять таки не забудь убраться", LocalDate.parse("2023-10-12"), Status.TODO));
    }
}
