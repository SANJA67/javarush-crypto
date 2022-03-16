package ru.javarush.cryptocode.cryptography;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Crypto {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

    public List<String> encryption(List<String> lineBuffer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter shift step. It must be a number between two and five.");
        try {
            int shift = scanner.nextInt();
            if (!(shift >= 2 && shift <= 5)) {
                System.out.println("Output a number that does not match the condition.");
                encryption(lineBuffer);
            }

        } catch (InputMismatchException e) {
            System.out.println("You didn't enter a number.");
            encryption(lineBuffer);
        }
        return lineBuffer;
    }
}
