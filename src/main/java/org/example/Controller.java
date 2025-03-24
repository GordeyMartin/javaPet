package org.example;

import org.example.repository.Status;
import org.example.repository.Task;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Controller {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        Service myService = new Service();

        boolean stopped = false;
        while (!stopped) {
            System.out.println("Введите команду");
            String command = myScanner.nextLine();

            if (command.equals("add")) {
                System.out.println("Введите название задачи");
                String name = myScanner.nextLine();

                System.out.println("Введите описание задачи");
                String description = myScanner.nextLine();

                System.out.println("Введите срок выполнения задачи");
                String deadline = myScanner.nextLine();

                System.out.println("Введите статус задачи (todo, in progress, done)");
                String statusString = myScanner.nextLine();
                try {
                    Status status = Service.findStatusByName(statusString);
                    myService.addTask(name, description, deadline, status);
                } catch (NoSuchElementException e) {
                    System.out.println("Нет такого статуса");
                    continue;
                }
            }

            if (command.equals("list")) {
                for (Task t: myService.getListOfAllTasks()) {
                    System.out.println(t);
                }
            }

            if (command.equals("edit")) {
                System.out.println("Какую задачу вы хотите изменить? (Напишите имя задачи)");
                String name = myScanner.nextLine();
                try{
                    Task task = myService.findTaskByName(name);

                    System.out.println("Что вы хотите изменить? (описание, срок выполнения, статус)");
                    String changeWanted = myScanner.nextLine();

                    if (changeWanted.equals("описание")) {
                        System.out.println("Какое будет новое описание?");
                        String newDescription = myScanner.nextLine();
                        myService.changeTaskDescription(task, newDescription);
                    }
                    if (changeWanted.equals("срок выполнения")) {
                        System.out.println("Какой будет новый срок выполнения?");
                        String newDeadline = myScanner.nextLine();
                        myService.changeTaskDeadline(task, newDeadline);
                    }
                    if (changeWanted.equals("статус")) {
                        System.out.println("Какой будет новый статус?");
                        try {
                            Status newStatus = Service.findStatusByName(myScanner.nextLine());
                            myService.changeTaskStatus(task, newStatus);
                        } catch (NoSuchElementException e) {
                            System.out.println("Нет такого статуса");
                            continue;
                        }
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Такой задачи нет");
                    continue;
                }
            }
            if (command.equals("delete")) {
                System.out.println("Какую задачу вы хотите изменить? (Напишите имя задачи)");
                String name = myScanner.nextLine();
                try {
                    Task task = myService.findTaskByName(name);
                    myService.deleteTask(task);
                } catch (NoSuchElementException e) {
                    System.out.println("Нет такой задачи");
                    continue;
                }
            }
            if (command.equals("filter")) {
                System.out.println("Задачи с каким статусом вы ищете? (todo, in progress, done)");
                try {
                    Status status = Service.findStatusByName(myScanner.nextLine());
                    System.out.println(myService.filterTasksByStatus(status));
                } catch (NoSuchElementException e) {
                    System.out.println("Нет такого статуса");
                    continue;
                }
            }
            if (command.equals("sort")) {
                System.out.println(myService.sortTasks());
            }
            if (command.equals("exit")) {
                stopped = true;
            }
            System.out.println("Действие прошло успешно");
        }
    }
}
