package org.example;

import org.example.data.Status;
import org.example.data.Task;

import java.time.LocalDate;
import java.util.Scanner;

public class Service {
    Scanner myScanner;
    Repository myRepository;

    public Service(Scanner myScanner, Repository myRepository) {
        this.myScanner = myScanner;
        this.myRepository = myRepository;
    }

    public void addTask(){
        System.out.println("Введите название задачи");
        String nameToAdd = myScanner.nextLine();

        System.out.println("Введите описание задачи");
        String description = myScanner.nextLine();

        System.out.println("Введите срок выполнения задачи. Используйте ГГГГ-ММ-ДД");
        String deadlineString = myScanner.nextLine();
        LocalDate deadline;
        deadline = LocalDate.parse(deadlineString);
        System.out.println("Введите статус задачи (todo, in progress, done)");
        String statusString = myScanner.nextLine();
        Status status = Repository.findStatusByName(statusString);
        myRepository.addTask(nameToAdd, description, deadline, status);
    }

    public void listTasks() {
        for (Task t : myRepository.getListOfAllTasks()) {
            System.out.println(t);
        }
    }

    public void editTask() {
        System.out.println("Какую задачу вы хотите изменить? (Напишите имя задачи)");
        String nameToEdit = myScanner.nextLine();
        Task task = myRepository.findTaskByName(nameToEdit);

        System.out.println("Что вы хотите изменить? (описание, срок выполнения, статус)");
        String changeWanted = myScanner.nextLine();

        if (changeWanted.equals("описание")) {
            System.out.println("Какое будет новое описание?");
            String newDescription = myScanner.nextLine();
            myRepository.changeTask(task, newDescription, "description");
        }
        if (changeWanted.equals("срок выполнения")) {
            System.out.println("Какой будет новый срок выполнения?");
            String newDeadlineString = myScanner.nextLine();
            LocalDate newDeadline;
            newDeadline = LocalDate.parse(newDeadlineString);
            myRepository.changeTask(task, newDeadline, "deadline");
        }
        if (changeWanted.equals("статус")) {
            System.out.println("Какой будет новый статус?");
            Status newStatus = Repository.findStatusByName(myScanner.nextLine());
            myRepository.changeTask(task, newStatus, "status");
        }
    }

    public void deleteTask() {
        System.out.println("Какую задачу вы хотите изменить? (Напишите имя задачи)");
        String nameToDelete = myScanner.nextLine();
        Task task = myRepository.findTaskByName(nameToDelete);
        myRepository.deleteTask(task);
    }

    public void filterTasks() {
        System.out.println("Задачи с каким статусом вы ищете? (todo, in progress, done)");
        Status status = Repository.findStatusByName(myScanner.nextLine());
        System.out.println(myRepository.filterTasksByStatus(status));
    }
    public void sortTasks() {
        System.out.println(myRepository.sortTasks());
    }
}
