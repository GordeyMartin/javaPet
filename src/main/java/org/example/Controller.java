package org.example;

import org.example.exceptions.TaskNameDublicateException;
import org.example.data.Command;
import org.example.data.Status;
import org.example.data.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Controller {
    public static void main(String[] args) {
        try {
            Scanner myScanner = new Scanner(System.in);
            Repository myRepository = new Repository();
            Service myService = new Service(myScanner, myRepository);

            boolean stopped = false;
            while (!stopped) {
                try {
                    System.out.println("Введите команду");
                    String commandString = myScanner.nextLine();
                    Command command = Repository.findCommandByName(commandString);
                    switch (command) {
                        case Command.ADD:
                            myService.addTask();
                            break;

                        case Command.LIST:
                            myService.listTasks();
                            break;

                        case Command.EDIT:
                            myService.editTask();
                            break;

                        case Command.DELETE:
                            myService.deleteTask();
                            break;

                        case Command.FILTER:
                            myService.filterTasks();
                            break;

                        case Command.SORT:
                            myService.sortTasks();
                            break;

                        case Command.EXIT:
                            stopped = true;
                            break;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Неверный формат даты. Используйте ГГГГ-ММ-ДД");
                } catch (TaskNameDublicateException e) {
                    System.out.println("Задача с таким именем уже есть");
                } catch (NoSuchElementException e) {
                    System.out.println("Нет такого варианта");
                } catch (Exception e) {
                    System.out.println("Неизвестный ввод");
                }
            }
        } catch (Exception e) {
            System.out.println("Неизвестный ввод. Приложение перезапускается");
            main(args);
        }
    }
}
