package ru.javarush.cryptocode.consoleui;

import ru.javarush.cryptocode.cryptography.Crypto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dialogue {
    private List<String> lineBuffer = new ArrayList<>();

    public void start() {
/*
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path to the file to read");
        String pathToReadFile = scanner.nextLine();
        System.out.println("Enter the path to the file to write");
        String pathToWriteFile = scanner.nextLine();
*/

        try (BufferedReader reader = new BufferedReader(new FileReader("file1.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {

                lineBuffer.add(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for (String value : lineBuffer) {
            System.out.println(value);
        }

        lineBuffer = new Crypto().encrypts(lineBuffer);
        for (String value : lineBuffer) {
            System.out.println(value);
        }
/*
        try (BufferedWriter writter = new BufferedWriter(new FileWriter("file2.txt"))) {
            for (String value : array) {
                writter.write(value + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
*/
    }
}
