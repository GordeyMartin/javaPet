package org.example;

import org.example.repository.Command;
import org.example.repository.Status;
import org.example.repository.Task;

import java.time.LocalDate;
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
    static Command findCommandByName(String name) {
        for (Command c : Command.values()) {
            if (name.equals(c.name)) {
                return c;
            }
        }
        throw new NoSuchElementException("Нет такой команды");
    }

    void addTask(String name, String description, LocalDate deadline, Status status) {
        try {
            this.findTaskByName(name);
            throw new TaskNameDublicateException("Задача с таким именем уже есть");
        } catch (NoSuchElementException e) {
            Task newTask = new Task(name, description, deadline, status);
            listOfAllTasks.add(newTask);
        }
    }

    void addTaskbyTask(Task newTask) { //только для тестов
        try {
            this.findTaskByName(newTask.getName());
            throw new TaskNameDublicateException("Задача с таким именем уже есть");

        } catch (NoSuchElementException e) {
            listOfAllTasks.add(newTask);
        }
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
    void changeTaskDeadline(Task task, LocalDate deadline) {
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
                .filter(task -> task.getTaskStatus() == status)
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
