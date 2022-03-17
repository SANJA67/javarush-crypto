package ru.javarush.cryptocode.consoleui;

import ru.javarush.cryptocode.cryptography.Crypto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dialogue {
    private List<String> lineBuffer = new ArrayList<>();
    private boolean flagToSelectAction = true;
    private final String ENCRYPT_FILE = "source file1.txt";
    private final String DECIPHER_FILE = "source file2.txt";
    private String workWithFile;

    public void start() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select actions to encrypt or decrypt:");
        System.out.print("encrypt enter \"YES\" or ");
        System.out.println("decipher enter \"NO\" ");
        String flag = null;
        try {
            flag = scanner.nextLine();
            if (!(flag.equals("YES")) && !(flag.equals("NO"))) {
                throw new Exception("Please specify YES or NO");
            }
        } catch (Exception e) {
            System.out.println(e);
            start();
        }
        if (flag.equals("NO")) {
            flagToSelectAction = false;
        }

        if (flagToSelectAction) {
            workWithFile = ENCRYPT_FILE;
        } else {
            workWithFile = DECIPHER_FILE;
        }
/*
        System.out.println("Enter the path to the file to read");
        String pathToReadFile = scanner.nextLine();
        System.out.println("Enter the path to the file to write");
        String pathToWriteFile = scanner.nextLine();
*/
        try (BufferedReader reader = new BufferedReader(new FileReader(workWithFile))) {
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
        try (BufferedWriter writter = new BufferedWriter(new FileWriter("source file2.txt"))) {
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
