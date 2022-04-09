package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
                    tasks = addTask(tasks);
                    break;
                case "2":
                case "remove":
                    tasks = removeTask(tasks);
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
        saveTasks(tasks, filename);
        System.out.println(ConsoleColors.RED + "Bye, bye!" + ConsoleColors.RESET);

    }

    public static int countLines(String filename) {
        Path path1 = Paths.get(filename);

        long lines = 0;

        try {
            lines = Files.lines(path1).count();

        } catch (IOException e) {
            System.out.println("File access problem, " + e.getMessage());
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

                for (int j = 0; j < tasks[i].length; j++) {
                    tasks[i][j] = tasks[i][j].trim();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku " + filename);
        }

        return tasks;
    }

    public static String[][] addTask(String[][] tasks) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Task name : ");
        String name = scan.nextLine();
        //todo: valid inputs

        System.out.print("Due date : ");
        String date = scan.nextLine();

        System.out.print("Is it important? true/false : ");
        String important = scan.nextLine();

        String[] task = {name, date, important};

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = task;

        return tasks;
    }

    public static boolean isValidIndex(String[][] tasks, String number) {
        try {
            int numb = Integer.parseInt(number);
            if (numb >= 0 && numb < tasks.length) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String[][] removeTask(String[][] tasks) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Please select number to remove (or press enter to cancel): ");
        String number = scan.nextLine();
        if ("".equals(number)) {
            return tasks;
        }


        while(!isValidIndex(tasks, number)) {
            System.out.print("Please select number to remove (or press enter to cancel): ");
            number = scan.nextLine();
            if ("".equals(number)) {
                return tasks;
            }
        }

        int numb = Integer.parseInt(number);

        System.out.println("You're trying to remove this task: ");
        printTask(tasks[numb], numb);

        System.out.print("Type yes to confirm : ");
        String confirm = scan.nextLine();

        if (confirm.equals("yes")) {
            tasks = ArrayUtils.remove(tasks, numb);
            System.out.println("Task has been removed");
        } else {
            System.out.println("Task hasn't been removed");
        }

        return tasks;
    }

    public static void printTask(String[] task, int i) {
        System.out.printf("[%s] %-40s %s %s\n", i, task[0], task[1], task[2]);
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

    public static void saveTasks(String[][] tasks, String filename) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(filename);

            for (int i = 0; i < tasks.length; i++) {
                printWriter.println(tasks[i][0] + ", " + tasks[i][1] + ", " + tasks[i][2]);
            }

            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
