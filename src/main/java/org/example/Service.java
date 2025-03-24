package org.example;

import org.example.repository.Status;
import org.example.repository.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Service {
    List<Task> listOfAllTasks = new ArrayList<>();

    static Status findStatusByName(String name) {
        for (Status s : Status.values()) {
            if (name.equals(s.name)) {
                return s;
            }
        }
        throw new NoSuchElementException("Нет такого варианта для статуса задачи");
    }

    void addTask(String name, String description, String deadline, Status status) {
        Task newTask = new Task(name, description, deadline, status);
        listOfAllTasks.add(newTask);
    }

    void addTaskbyTask(Task newTask) {
        listOfAllTasks.add(newTask); //только для тестов
    }

    List<Task> getListOfAllTasks(){
        return listOfAllTasks;
    }

    Task findTaskByName(String name) {
        Task foundTask = listOfAllTasks.stream()
                .filter(task -> task.getName().equals(name))
                .findFirst()
                .orElseThrow();
        return foundTask;
    }
    void changeTaskDescription(Task task, String description) {
        task.setDescription(description);
    }
    void changeTaskDeadline(Task task, String deadline) {
        task.setDeadline(deadline);
    }
    void changeTaskStatus(Task task, Status status) {
        task.setTaskStatus(status);
    }
    void deleteTask(Task task) {
        listOfAllTasks.remove(task);
    }

    List<Task> filterTasksByStatus(Status status) {
        List<Task> filteredList = listOfAllTasks.stream()
                .filter(task -> task.getTaskStatus().equals(status))
                .toList();
        return(filteredList);
    }
    List<Task> sortTasks() {
        List<Task> sortedList = listOfAllTasks.stream()
                .sorted( (a,b) -> a.getName().compareTo(b.getName()))
                .toList();
        return sortedList;
    }
}
