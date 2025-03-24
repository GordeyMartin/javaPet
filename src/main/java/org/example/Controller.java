package org.example;

import org.example.repository.Command;
import org.example.repository.Status;
import org.example.repository.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Controller {
    public static void main(String[] args) {
        try {
            Scanner myScanner = new Scanner(System.in);
            Service myService = new Service();

            boolean stopped = false;
            while (!stopped) {
                try {
                    System.out.println("Введите команду");
                    String commandString = myScanner.nextLine();
                    Command command = Service.findCommandByName(commandString);
                    switch (command) {
                        case Command.ADD:
                            System.out.println("Введите название задачи");
                            String nameToAdd = myScanner.nextLine();

                            System.out.println("Введите описание задачи");
                            String description = myScanner.nextLine();

                            System.out.println("Введите срок выполнения задачи. Используйте ГГГГ-ММ-ДД");
                            String deadlineString = myScanner.nextLine();
                            LocalDate deadline;
                            try {
                                deadline = LocalDate.parse(deadlineString);
                            } catch (DateTimeParseException e) {
                                System.out.println("Неверный формат даты. Используйте ГГГГ-ММ-ДД");
                                continue;
                            }
                            System.out.println("Введите статус задачи (todo, in progress, done)");
                            String statusString = myScanner.nextLine();
                            try {
                                Status status = Service.findStatusByName(statusString);
                                try {
                                    myService.addTask(nameToAdd, description, deadline, status);
                                } catch (TaskNameDublicateException e) {
                                    System.out.println("Задача с таким именем уже есть");
                                    continue;
                                }
                            } catch (NoSuchElementException e) {
                                System.out.println("Нет такого статуса");
                                continue;
                            }
                            break;

                        case Command.LIST:
                            for (Task t: myService.getListOfAllTasks()) {
                                System.out.println(t);
                            }
                            break;

                        case Command.EDIT:
                            System.out.println("Какую задачу вы хотите изменить? (Напишите имя задачи)");
                            String nameToEdit = myScanner.nextLine();
                            try{
                                Task task = myService.findTaskByName(nameToEdit);

                                System.out.println("Что вы хотите изменить? (описание, срок выполнения, статус)");
                                String changeWanted = myScanner.nextLine();

                                if (changeWanted.equals("описание")) {
                                    System.out.println("Какое будет новое описание?");
                                    String newDescription = myScanner.nextLine();
                                    myService.changeTask(task, newDescription, "description");
                                }
                                if (changeWanted.equals("срок выполнения")) {
                                    System.out.println("Какой будет новый срок выполнения?");
                                    String newDeadlineString = myScanner.nextLine();
                                    LocalDate newDeadline;
                                    try {
                                        newDeadline = LocalDate.parse(newDeadlineString);
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Неверный формат даты. Используйте ГГГГ-ММ-ДД");
                                        continue;
                                    }
                                    myService.changeTask(task, newDeadline, "deadline");
                                }
                                if (changeWanted.equals("статус")) {
                                    System.out.println("Какой будет новый статус?");
                                    try {
                                        Status newStatus = Service.findStatusByName(myScanner.nextLine());
                                        myService.changeTask(task, newStatus, "status");
                                    } catch (NoSuchElementException e) {
                                        System.out.println("Нет такого статуса");
                                        continue;
                                    }
                                }
                            } catch (NoSuchElementException e) {
                                System.out.println("Такой задачи нет");
                                continue;
                            }
                            break;

                        case Command.DELETE:
                            System.out.println("Какую задачу вы хотите изменить? (Напишите имя задачи)");
                            String nameToDelete = myScanner.nextLine();
                            try {
                                Task task = myService.findTaskByName(nameToDelete);
                                myService.deleteTask(task);
                            } catch (NoSuchElementException e) {
                                System.out.println("Нет такой задачи");
                                continue;
                            }
                            break;

                        case Command.FILTER:
                            System.out.println("Задачи с каким статусом вы ищете? (todo, in progress, done)");
                            try {
                                Status status = Service.findStatusByName(myScanner.nextLine());
                                System.out.println(myService.filterTasksByStatus(status));
                            } catch (NoSuchElementException e) {
                                System.out.println("Нет такого статуса");
                                continue;
                            }
                            break;

                        case Command.SORT:
                            System.out.println(myService.sortTasks());
                            break;

                        case Command.EXIT:
                            stopped = true;
                            break;
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Такой команды нет");
                    continue;
                }
            }
        } catch (Exception e) {
            System.out.println("Неизвестный ввод. Приложение перезапускается");
            main(args);
        }
    }
}
