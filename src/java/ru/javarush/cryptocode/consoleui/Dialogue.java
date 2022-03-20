package ru.javarush.cryptocode.consoleui;

import ru.javarush.cryptocode.cryptography.Crypto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dialogue {

    private static final String FIRST_SETTING = "Select actions encrypt or decrypt:\n" +
            "encrypt enter \"YES\" or decipher enter \"NO\"";
    private static final String SECOND_SETTINGS = "How to decrypt\n" +
            "if by key enter \"YES\" if static method type \"NO\"";

    private List<String> lineBuffer = new ArrayList<>();

    private boolean flagToSelectAction = true;
    private boolean flagToSelectMethod = true;

    Scanner scanner = new Scanner(System.in);
    String workWithFile;
    String result;


    String flag;

    public void start() {

        setUpTheProgram(FIRST_SETTING);

        if (flag.equals("NO")) {
            flagToSelectAction = false;
        }

        if (!flagToSelectAction) {
            setUpTheProgram(SECOND_SETTINGS);

            if (flag.equals("NO")) {

                flagToSelectMethod = false;

            }
        }

        enterThePathToTheFileToRead("");

        lineBuffer = new Crypto().encrypts(lineBuffer, flagToSelectAction, flagToSelectMethod);

        enterThePathToTheFileToWriting("");

    }

    private void setUpTheProgram(String firstSetting) {
        System.out.println(firstSetting);

        try {
            flag = scanner.nextLine();
            if (!(flag.equals("YES")) && !(flag.equals("NO"))) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Please specify YES or NO.");
            setUpTheProgram(firstSetting);
        }
    }

    private void enterThePathToTheFileToRead(String message) {
        System.out.println(message);
        System.out.println("Enter the path to the file to read:");
        workWithFile = scanner.nextLine();
        readDataFromFileAndSendForProcessing();
    }

    private void enterThePathToTheFileToWriting(String message) {
        System.out.println(message);
        System.out.println("Enter the path to the file to write:");
        result = scanner.nextLine();
        writingDataToAFileReceivedAfterProcessing();
    }

    private void readDataFromFileAndSendForProcessing() {

        try (BufferedReader reader = new BufferedReader(new FileReader(workWithFile))) {
            String line;
            while ((line = reader.readLine()) != null) {

                lineBuffer.add(line);
            }
        } catch (IOException e) {

            System.out.println(e + "File path is incorrect");

            enterThePathToTheFileToRead("File not found");
        }
    }

    private void writingDataToAFileReceivedAfterProcessing() {
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(result))) {
            for (String value : lineBuffer) {
                writter.write(value + "\n");
            }
        } catch (IOException e) {

            System.out.println(e + "File path is incorrect");

            enterThePathToTheFileToWriting("File not found");
        }
    }
}
