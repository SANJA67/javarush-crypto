package ru.javarush.cryptocode.cryptography;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Crypto {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    private int characterNumber;
    private static final int NUMBER_OF_CHARACTERS_IN_THE_ARRAY = 10;

    public List<String> encrypts(List<String> lineBuffer, boolean flagToSelectAction) {

        List<String> copyLineBuffer = new ArrayList<>(lineBuffer);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the encryption key.");
        int key = 0;

        try {
            key = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("You didn't enter a number.");
            encrypts(lineBuffer, flagToSelectAction);
        }

        lineBuffer.clear();
        char[] encryptedLine;
        boolean uppercase = true;
        boolean lowerCase = false;

        for (String line : copyLineBuffer) {

            encryptedLine = new char[line.length()];

            for (int i = 0; i < line.length(); i++) {

                if (checks(line.charAt(i))) {

                    if (Character.isUpperCase(line.charAt(i))) {
                        encryptedLine[i] =
                                Character.toUpperCase(ALPHABET[determineEncodingIndex(key, flagToSelectAction, uppercase)]);
                    } else {
                        encryptedLine[i] = ALPHABET[determineEncodingIndex(key, flagToSelectAction, lowerCase)];
                    }
                } else {
                    encryptedLine[i] = line.charAt(i);
                }
            }
            lineBuffer.add(new String(encryptedLine));
        }
        return lineBuffer;

    }

    private int determineEncodingIndex(int key, boolean flagToSelectAction, boolean flag) {

        if (!flagToSelectAction) {
            key = -1 * key;
        }

        int arrayLengthAdjustment = 1;
        int index = characterNumber + key;
        int noita = NUMBER_OF_CHARACTERS_IN_THE_ARRAY;


        if (flag) {
            int output1 = index % (ALPHABET.length - noita) - arrayLengthAdjustment;

            if (output1 < 0) {
                output1 = output1 + (ALPHABET.length - noita);
            }

            return output1;

        } else {

            int output2 = index % (ALPHABET.length) - arrayLengthAdjustment;

            if (output2 < 0) {
                output2 = output2 + (ALPHABET.length);
            }

            return output2;
        }
    }

    private boolean checks(Character symbol) {

        characterNumber = 0;
        symbol = Character.toLowerCase(symbol);

        for (Character characterFromArray : ALPHABET) {

            characterNumber++;

            if (characterFromArray.equals(symbol)) {
                return true;
            }
        }

        return false;
    }
}
