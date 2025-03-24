package org.example;

import org.example.repository.Status;
import org.example.repository.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ServiceTest {
    Service myService;

    @BeforeEach
    void addSomeTasks() {
        myService = new Service();
        myService.addTask("Уборка", "Не забудь убраться", LocalDate.parse("2004-12-12"), Status.TODO);
        myService.addTask("Выучи Java", "Стань супер разработчиком", LocalDate.parse("2020-10-12"), Status.IN_PROGRESS);
        myService.addTask("Пройди Stream API", "Закончи step по Stream API в Java", LocalDate.parse("2014-11-11"), Status.DONE);
    }

    @Test
    void findStatusByNameTest() {
        String name = "in progress";
        Service.findStatusByName(name);
        Assertions.assertEquals(Status.IN_PROGRESS, Service.findStatusByName(name));
    }

    @Test
    void findTaskByNameTest() {
        Assertions.assertEquals("Уборка", myService.findTaskByName("Уборка").getName());
    }

    @Test
    void addTaskTest() {
        myService.addTask("Новое дело", "Описание нового дела", LocalDate.parse("2021-10-12"), Status.TODO);
        Assertions.assertEquals("Новое дело", myService.findTaskByName("Новое дело").getName());
    }

    @Test
    void changeTaskTest() {
        myService.changeTask(myService.findTaskByName("Уборка"), Status.DONE, "status");
        Assertions.assertEquals(Status.DONE, myService.findTaskByName("Уборка").getTaskStatus());
    }

    @Test
    void deleteTaskTest() {
        myService.deleteTask(myService.findTaskByName("Уборка"));
        Assertions.assertThrows(NoSuchElementException.class, () -> myService.findTaskByName("Уборка"));
    }

    @Test
    void filterTasksByStatusTest() {
        Status status = Status.DONE;
        myService.deleteTask(myService.findTaskByName("Пройди Stream API"));
        List<Task> listOfTasks = Arrays.asList();
        Assertions.assertEquals(listOfTasks, myService.filterTasksByStatus(Status.DONE));
    }

    @Test
    void sortTasksTest() {
        myService.deleteTask(myService.findTaskByName("Пройди Stream API"));
        myService.deleteTask(myService.findTaskByName("Уборка"));
        myService.deleteTask(myService.findTaskByName("Выучи Java"));

        Task task1 = new Task("1", "1", LocalDate.parse("2020-10-12"), Status.DONE);
        Task task2 = new Task("2", "2", LocalDate.parse("2021-10-12"), Status.TODO);
        List<Task> listOfStacks = Arrays.asList(task1, task2);
        myService.addTaskbyTask(task2);
        myService.addTaskbyTask(task1);

        Assertions.assertEquals(listOfStacks, myService.sortTasks());
    }

    @Test
    void taskNameDublicateTest() {
        Assertions.assertThrows(TaskNameDublicateException.class, () -> myService.addTask("Уборка", "Опять таки не забудь убраться", LocalDate.parse("2023-10-12"), Status.TODO));
    }
}
