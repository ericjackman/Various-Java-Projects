import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Knapsack {
    private static ArrayList<int[]> fileToArrayList(String file) {
        /**
         * Reads txt file containing projects and creates an ArrayList to store weeks and profits
         * @param   file    The file to be read
         * @return ArrayList containing the weeks and profits of all projects
         */
        ArrayList<int[]> projects = new ArrayList<>();  // Stores projects as [weeks, profit]
        int numProjects = 0;  // Number of projects in input file

        try (Scanner scan = new Scanner(new File(file))) {
            while (scan.hasNextLine()) {
                scan.next();
                // Add project weeks and profit to arraylist
                projects.add(new int[]{Integer.parseInt(scan.next()), Integer.parseInt(scan.next())});
                numProjects++;
            }
            System.out.println("Number of projects = " + numProjects);
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        } catch (NoSuchElementException ex) {
            //Extra line at end of file
            System.out.println("Number of projects = " + numProjects);
        }
        return projects;
    }


    private static int[][] knapsackTable(int weight, ArrayList<int[]> projects) {
        /**
         * Creates int[][] table representing a table for a generic knapsack problem
         * @param   weight      The weight capacity
         * @param   projects    The weights and profits of all available items
         * @return List of lists representing knapsack problem
         */
        // Weight (weeks) of item (project) j given by projects.get(j - 1)[0]
        // Value (profit) of item (project) j given by projects.get(j - 1)[1]
        int[][] table = new int[weight + 1][projects.size() + 1];

        for (int w = 0; w <= weight; w++) {  // Iterate through rows by weight
            for (int j = 0; j <= projects.size(); j++) {  // Iterate through columns by item
                if (w == 0 || j == 0) table[w][j] = 0;
                else if (projects.get(j - 1)[0] > w) table[w][j] = table[w][j - 1];
                    // table[w][j] = max of...
                    // table[w - weight of item j][j - 1] + value of item j
                    // table[w][j - 1]
                else table[w][j] = Math.max(
                            ((table[(w - projects.get(j - 1)[0])][(j - 1)]) + projects.get(j - 1)[1]),
                            (table[w][j - 1]));
            }
        }
        return table;
    }


    private static String tableAnalysis(int[][] table, ArrayList<int[]> projects) {
        /**
         * Uses the knapsack table to determine profit and projects chosen
         * @param   table       knapsack table
         * @param   projects    weeks and profits of projects
         * @return String of profit and projects chosen
         */
        int currentW = table.length - 1;  // Weeks index of element being checked
        int currentJ = table[table.length - 1].length - 1;  // Projects index of element being checked
        int currentVal = table[currentW][currentJ];  // Profit value of element being checked
        ArrayList<Integer> projectsChosen = new ArrayList<>();  // Stores index of projects chosen

        int profit = currentVal;  // Total profit is last element in table

        while (currentW != 0 && currentJ != 0) {
            if (currentVal == table[currentW][currentJ - 1]) currentJ--;  // Project was not chosen
            else {  // Project was chosen
                projectsChosen.add(currentJ - 1);  // Add project index to arraylist
                int targetVal = currentVal - projects.get(currentJ - 1)[1];  // Total profit - profit of project chosen
                currentJ--;
                for (int w = 0; w < currentW; w++) {  // Find next current
                    if (table[w][currentJ] == targetVal) {
                        currentW = w;
                        currentVal = table[w][currentJ];
                        break;
                    }
                }
            }
        }

        // Build string containing available projects, available weeks, number of projects, and total profit
        StringBuilder str = new StringBuilder("Number of projects available: " + projects.size() +
                "\nAvailable employee work weeks: " + (table.length - 1) + "\nNumber of projects chosen: " +
                projectsChosen.size() + "\nTotal profit: " + profit);

        // Add projects chosen to string
        int project;
        for (int i = projectsChosen.size() - 1; i >= 0; i--) {
            project = projectsChosen.get(i);
            str.append("\nProject" + project + " " + projects.get(project)[0] + " " + projects.get(project)[1]);
        }
        return str.toString();
    }


    private static void writeToFile(String str, String file) {
        /**
         * Writes string to txt file
         * @param   str     String to be written
         * @param   file    File to be written to
         */
        try (PrintWriter out = new PrintWriter(new File(file))) {
            out.print(str);  // Write string
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        }
        System.out.println("Done");
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);  // Scanner to read user inputs
        int weeks = -1;  // Employee work weeks
        while (weeks < 0) {  // Repeat until user gives appropriate input
            System.out.print("Enter the number of available employee work weeks: ");
            weeks = scan.nextInt();
            if (weeks < 0) System.out.println("Weeks cannot be negative.");
        }
        System.out.print("Enter the name of the input file: ");
        String input = scan.next();  // File to read
        System.out.print("Enter the name of the output file: ");
        String output = scan.next();  // File to write

        ArrayList<int[]> projects = fileToArrayList(input);
        int[][] table = knapsackTable(weeks, projects);
        String str = tableAnalysis(table, projects);
        writeToFile(str, output);
    }
}
