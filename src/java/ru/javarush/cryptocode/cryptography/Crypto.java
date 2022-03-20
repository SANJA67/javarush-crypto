package ru.javarush.cryptocode.cryptography;

import java.util.*;

public class Crypto {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

    private int characterNumber;
    private static final int NUMBER_OF_CHARACTERS_IN_THE_ARRAY = 10;
    int key = 0;

    List<String> lineBufferConverted;

    public List<String> encrypts(List<String> lineBuffer, boolean flagToSelectAction, boolean flagToSelectMethod) {

        if (!flagToSelectAction && !flagToSelectMethod) {
            lineBufferConverted = decodingByStatisticalMethod(lineBuffer, flagToSelectAction);
        } else {
            lineBufferConverted = workByKey(enterTheKey(), lineBuffer, flagToSelectAction);

        }
       return lineBufferConverted;
    }

    private List<String> workByKey(int key, List<String> lineBuffer, boolean flagToSelectAction) {

        List<String> copyLineBuffer = new ArrayList<>(lineBuffer);
        lineBuffer.clear();
        char[] encryptedLine;
        boolean upperCase = true;
        boolean lowerCase = false;

        for (String line : copyLineBuffer) {

            encryptedLine = new char[line.length()];

            for (int i = 0; i < line.length(); i++) {

                if (checks(line.charAt(i))) {

                    if (Character.isUpperCase(line.charAt(i))) {
                        encryptedLine[i] =
                                Character.toUpperCase(ALPHABET[determineEncodingIndex(key, flagToSelectAction, upperCase)]);
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

    private List<String> decodingByStatisticalMethod(List<String> lineBuffer, boolean flagToSelectAction) {
        int[] howCommonIsTheSymbol = new int[ALPHABET.length];

        for (String line : lineBuffer) {

            for (int j = 0; j < line.length(); j++) {
                for (int i = 0; i < ALPHABET.length; i++) {     // здесь производят то почёт сколько раз символ встречается в тексте
                    if (line.charAt(j) == ALPHABET[i]) {
                        howCommonIsTheSymbol[i]++;
                    }
                }
            }
        }

        int[] howCommonIsTheSymbolCopy = Arrays.copyOf(howCommonIsTheSymbol, howCommonIsTheSymbol.length);
        Arrays.sort(howCommonIsTheSymbolCopy);
        int numberOfOptionsUsed = 10;                           // выбираю 10 символов чаще всего повторяющихся в тексте
        int[] characterNumberInArray = new int[numberOfOptionsUsed]; // в этом массиве сохраняются индексы чаще всего
                                                                    // повторяющихся символов, индексы массива алфавит

        for (int i = 0; i < characterNumberInArray.length; i++) {
            for (int j = 0; j < howCommonIsTheSymbol.length; j++) {
                if (howCommonIsTheSymbolCopy[howCommonIsTheSymbolCopy.length - i - 1] == howCommonIsTheSymbol[j]) {
                    characterNumberInArray[i] = j + 1;
                }
            }
        }

        int[] decryptionKeys = Arrays.copyOf(characterNumberInArray, characterNumberInArray.length);

        // в данном конкретном случае массив
        // characterNumberInArray уже содержит ключ
        // так как я выбра пробел для вычисления ключа, а он находится под
        // последним в массиве, если бы символ на которой я ориентируюсь, находился бы под другим индексом мне
        // бы пришлось сделать дополнительный расчёт

        List<String> newLineBuffer = new ArrayList<>(); // здесь сохраняются все варианты расшифровки
        for (int i = 1; i < decryptionKeys.length +1; i++) {// цифра 1 используется для читабельность и файла

            List<String> decryptionOption  = workByKey(decryptionKeys[i], lineBuffer, flagToSelectAction);
            // строка выше вызывает метод расшифровки по ключу отправляет в метод
            // вариант ключа и получает оттуда коллекцию который свою очередь сохраняется в коллекции newLineBuffer
            newLineBuffer.add("Option " + i);

            newLineBuffer.addAll(decryptionOption);

        }

        return newLineBuffer;
    }

    private int enterTheKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the encryption key.");


        try {
            key = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("You didn't enter a number.");
            enterTheKey();
        }
        return key;
    }

    private int determineEncodingIndex(int key, boolean flagToSelectAction, boolean flag) {

        if (!flagToSelectAction) {
            key = -1 * key;                 // если мы расшифровываем, то делаем ключ отрицательным
        }

        int arrayLengthAdjustment = 1;
        int intermediateIndex = characterNumber + key;
        int noita = NUMBER_OF_CHARACTERS_IN_THE_ARRAY;      // в случае если буква относится к букве верхнего регистра
                                                            // мы отнимаем количество символов несоответствующих букве из
                                                            // массива алфавит для того чтобы потом можно было её расшифровать

        if (flag) { //если буква верхнего регистра
            int finishedIndex1 = intermediateIndex % (ALPHABET.length - noita) - arrayLengthAdjustment;

            if (finishedIndex1 < 0) {
                finishedIndex1 = finishedIndex1 + (ALPHABET.length - noita);
            }

            return finishedIndex1;

        } else {

            int finishedIndex2 = intermediateIndex % (ALPHABET.length) - arrayLengthAdjustment;

            if (finishedIndex2 < 0) {
                finishedIndex2 = finishedIndex2 + (ALPHABET.length);
            }

            return finishedIndex2;
        }
    }

    private boolean checks(Character symbol) {

        characterNumber = 0;
        symbol = Character.toLowerCase(symbol);

        for (Character characterFromArray : ALPHABET) {

            characterNumber++;

            if (characterFromArray.equals(symbol)) {
                return true; // если символ найден в массиве алфавит значит true
            }
        }

        return false;
    }
}
