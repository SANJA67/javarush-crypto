package ru.javarush.cryptocode.consoleui;

import ru.javarush.cryptocode.cryptography.Crypto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dialogue {
    private List<String> lineBuffer = new ArrayList<>();
    private boolean flagToSelectAction = true;

    public void start() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select actions to encrypt or decrypt:");
        System.out.print("encrypt enter \"YES\" or decipher enter \"NO\"");
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

        System.out.println("Enter the path to the file to read");
        String workWithFile = scanner.nextLine();
        System.out.println("Enter the path to the file to write");
        String result = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(workWithFile))) {
            String line;
            while ((line = reader.readLine()) != null) {

                lineBuffer.add(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        lineBuffer = new Crypto().encrypts(lineBuffer, flagToSelectAction);

        try (BufferedWriter writter = new BufferedWriter(new FileWriter(result))) {
            for (String value : lineBuffer) {
                writter.write(value + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
