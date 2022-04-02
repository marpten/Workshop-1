package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TaskManager {


    public static void main(String[] args) {
        String filename = "tasks.csv";
        String[][] tasks = readTasks(filename);
        
        display_menu();
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        while (!("exit".equals(input.toLowerCase()) || "0".equals(input.toLowerCase()))) {
            switch (input) {
                case "1":
                case "add":
                    addTask();
                    break;
                case "2":
                case "remove":
                    addTask();
                    break;
                case "3":
                case "list":
                    listTasks(tasks);
                    break;
                default:
                    System.out.println(ConsoleColors.BLUE + "Please select a correct option.");
            }
            display_menu();
            input = scan.nextLine();
        }

    }

    public static int countLines(String filename) {
        Path path1 = Paths.get(filename);

        long lines = 0;

        try {
            lines = Files.lines(path1).count();

        } catch (IOException e) {
            System.out.println("File access problem");
            e.printStackTrace();
        }

        return (int) lines;
    }

    public static String[][] readTasks(String filename) {

        Path path = Paths.get(filename);

        int lines = countLines(filename);

        String[][] tasks = new String[lines][3];
        String[] tasks_lines = new String[lines];

        File file = new File(filename);

        try {
            Scanner scan = new Scanner(file);

            for (int i = 0; i < lines; i++) {
                tasks[i] = scan.nextLine().split(",");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku " + filename);
        }

        return tasks;
    }

    public static void addTask() {
        System.out.println("Adding tasks");
        //todo: adding task method
    }

    public static void removeTasks() {
        System.out.println("Removing tasks");
        //todo: removing task method
    }

    public static void listTasks(String[][] tasks) {

        if (tasks.length == 0) {
            System.out.println("There are no tasks on list");
            return;
        }

        for (int i = 0; i < tasks.length; i++) {
            //System.out.println("[" + i + "] " + tasks[i][0] + " " + tasks[i][0]);
            System.out.printf("[%s] %-40s %s %s\n", i, tasks[i][0], tasks[i][1], tasks[i][2]);

        }

        System.out.println();
    }

    public static void display_menu() {
        System.out.println(ConsoleColors.BLUE + "1.Add | 2.Remove | 3.List | 0.Exit" + ConsoleColors.RESET);
        System.out.print("> ");

    }
}
