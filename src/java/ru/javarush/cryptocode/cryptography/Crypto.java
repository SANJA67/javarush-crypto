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

    public List<String> encrypts(List<String> lineBuffer) {
        List<String> copyLineBuffer = new ArrayList<>(lineBuffer);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter shift step. It must be a number between two and five.");
        System.out.println(ALPHABET.length);
        int key = 0;
        try {
            key = scanner.nextInt();
            //if (!(key >= 2 && key <= 5)) {

            System.out.println("Output a number that does not match the condition.");
            //encryption(lineBuffer);
            //}

        } catch (InputMismatchException e) {
            System.out.println("You didn't enter a number.");
            encrypts(lineBuffer);
        }

        lineBuffer.clear();
        char[] encryptedLine;
        for (String line : copyLineBuffer) {
            encryptedLine = new char[line.length()];
            for (int i = 0; i < line.length(); i++) {
                if (checks(line.charAt(i))) {
                    if (Character.isUpperCase(line.charAt(i))) {
                        encryptedLine[i] = Character.toUpperCase(ALPHABET[determineEncodingIndex(line.charAt(i), key)]);
                    } else {
                        encryptedLine[i] = ALPHABET[determineEncodingIndex(line.charAt(i), key)];
                    }
                } else {
                    encryptedLine[i] = line.charAt(i);
                }
            }
            lineBuffer.add(new String(encryptedLine));
        }
        return lineBuffer;

    }

    private int determineEncodingIndex(char charAt, int key) {
        if (Character.isUpperCase(charAt) && characterNumber + key >
                2 * (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY)) {
            key -= ALPHABET.length - 2 * NUMBER_OF_CHARACTERS_IN_THE_ARRAY;
        } else if (characterNumber + key > ALPHABET.length + ALPHABET.length) {
            key -= ALPHABET.length;
        }

        /*
        System.out.println(ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY);

        if (Character.isUpperCase(charAt) && characterNumber + key > ALPHABET.length + ALPHABET.length) {

            key -= ALPHABET.length;
        } else {
            key -= ALPHABET.length;
        }
                {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
            (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY) -
                */
        if (Character.isUpperCase(charAt)) {
            if (characterNumber + key > ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY) {
                System.out.println("1 " + ((characterNumber + key) - (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY) - 1));
                System.out.println(characterNumber + key);
                System.out.println(ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY);
                return (characterNumber + key) - ALPHABET.length - 1;
            } else {
                System.out.println("2 " + (characterNumber + key - 1));
                return characterNumber + key - 1;
            }

        } else {
            if (characterNumber + key > ALPHABET.length) {
                return (characterNumber + key) - (ALPHABET.length) - 1;
            } else {
                return characterNumber + key - 1;
            }
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
