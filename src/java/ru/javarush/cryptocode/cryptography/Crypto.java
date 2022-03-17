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
                        Character.toUpperCase(ALPHABET[determineEncodingIndex(line.charAt(i),
                                key, flagToSelectAction, uppercase)]);
                    } else {
                        encryptedLine[i] =
                        ALPHABET[determineEncodingIndex(line.charAt(i),
                                key, flagToSelectAction, lowerCase)];
                    }
                } else {
                    encryptedLine[i] = line.charAt(i);
                }
            }
            lineBuffer.add(new String(encryptedLine));
        }
        return lineBuffer;

    }

    private int determineEncodingIndex(char charAt, int key, boolean flagToSelectAction, boolean flag) {
        System.out.println("Символ " + charAt);
        System.out.println("Ключ " + key);
        System.out.println("Флаг " + flagToSelectAction);
        System.out.println("Номер буквы " + characterNumber);
        if (!flagToSelectAction) {
            key = -1 * key;
        }
        int arrayLengthAdjustment = 1;
        int index = characterNumber + key - arrayLengthAdjustment;


        if (flag) {
            if (index > ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY) {
                System.out.println("Первый проход " + index % (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY));
                return index % (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY);
            } else if (index < 0) {
                System.out.println("Втрой проход " + index % (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY) +
                        (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY));
                return index % (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY) +
                        (ALPHABET.length - NUMBER_OF_CHARACTERS_IN_THE_ARRAY);
            } else {
                System.out.println("Третий проход " + index);
                return index;
            }
        } else {
            if (index > ALPHABET.length) {
                System.out.println("Четвертый проход " + index % (ALPHABET.length));
                return index % (ALPHABET.length);
            } else if (index < 0) {
                System.out.println("Пятый проход " + index % (ALPHABET.length) + (ALPHABET.length));
                return index % (ALPHABET.length) + (ALPHABET.length);
            } else {
                System.out.println("Шестой проход " + index);
                return index;
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
