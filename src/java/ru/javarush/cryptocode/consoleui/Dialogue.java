package ru.javarush.cryptocode.consoleui;

import ru.javarush.cryptocode.cryptography.Crypto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dialogue {

    public void start() {

        Scanner scanner = new Scanner(System.in);

        List<String> lineBuffer = new ArrayList<>();

        String secondSetting = "How to decrypt\n if by key enter \"1\" if static method type \"2\"";
        String firstSetting = "Select actions encrypt or decrypt:\n encrypt enter \"1\" or decipher enter \"2\"";

        boolean flagToSelectAction = true;
        boolean flagToSelectMethod = true;

        String flag = chooseACourseOfAction(firstSetting, scanner);
        if ("2".equals(flag)) {

            flagToSelectAction = false;
        }

        if (!flagToSelectAction) {

            flag = chooseACourseOfAction(secondSetting, scanner);

            if ("2".equals(flag)) {

                flagToSelectMethod = false;
            }
        }

        enterThePathToTheFileToRead("", scanner, lineBuffer);

        lineBuffer = new Crypto().encrypts(lineBuffer, flagToSelectAction, flagToSelectMethod, scanner);

        enterThePathToTheFileToWriting("", scanner, lineBuffer);

    }

    private String chooseACourseOfAction(String firstSetting, Scanner scanner) {

        System.out.println(firstSetting);
        String flag = null;

        try {
            flag = scanner.nextLine();
            if (!("1".equals(flag)) && !("2".equals(flag))) {
                throw new Exception();
            }
            return flag;
        } catch (Exception e) {
            System.out.println("Please specify 1 or 2.");
            chooseACourseOfAction(firstSetting, scanner);
        }

        return flag;
    }

    private void enterThePathToTheFileToRead(String message, Scanner scanner, List<String> lineBuffer) {

        System.out.println(message);
        System.out.println("Enter the path to the file to read:");

        String filenameToConvert = scanner.nextLine();

        readDataFromFileAndSendForProcessing(filenameToConvert, lineBuffer, scanner);

    }

    private void enterThePathToTheFileToWriting(String message, Scanner scanner, List<String> lineBuffer) {

        System.out.println(message);
        System.out.println("Enter the path to the file to write:");

        String fileNameForConversionResult = scanner.nextLine();

        writingDataToAFileReceivedAfterProcessing(fileNameForConversionResult, lineBuffer, scanner);
    }

    private void readDataFromFileAndSendForProcessing(String filenameToConvert, List<String> lineBuffer, Scanner scanner) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filenameToConvert))) {
            String line;
            while ((line = reader.readLine()) != null) {

                lineBuffer.add(line);
            }
        } catch (IOException e) {

            System.out.println(e + "File path is incorrect");

            enterThePathToTheFileToRead("File not found", scanner, lineBuffer);
        }
    }

    private void writingDataToAFileReceivedAfterProcessing
            (String fileNameForConversionResult, List<String> lineBuffer, Scanner scanner) {

        try (BufferedWriter writter = new BufferedWriter(new FileWriter(fileNameForConversionResult))) {
            for (String value : lineBuffer) {
                writter.write(value + "\n");
            }
        } catch (IOException e) {

            System.out.println(e + " File path is incorrect");

            enterThePathToTheFileToWriting("File not found", scanner, lineBuffer);
        }
    }
}
