package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
    static final int LONG_MAX = 65;
    static final int CANT_CHAR = 256;

    private int counter;
    private String regEx;

    public List<String> generate(String regEx, int numberOfResults) {
        this.regEx = regEx;
        List<String> examples = new ArrayList<>();
        for (int i = 0;i < numberOfResults;i++) {
            examples.add(generate());
        }
        return examples;
    }

    public String generate() {
        StringBuilder example = new StringBuilder();
        counter = 0;
        while (counter < regEx.length()) {
            if (regEx.charAt(counter) == '[') {
                example.append(caseCorchete());
            } else {
                if (regEx.charAt(counter) == '.') {
                    example.append(caseDot());
                } else {
                    if (regEx.charAt(counter) == '\\') {
                        example.append(caseBarra());
                    } else {
                        example.append(caseChar());
                    }
                }
            }
        }
        return example.toString();
    }

    boolean isCuantifier(char element) {
        return (element == '*' || element == '?' || element == '+');
    }

    String caseBarra() {
        String result = null;
        if ((counter + 1 != regEx.length()) && (counter + 2 != regEx.length()) && isCuantifier(regEx.charAt(counter + 2))) {
            result = getExCharCuantifier(regEx.charAt(counter + 2),Character.toString(regEx.charAt(counter + 1)));
            counter += 3;
        } else {
            if (counter + 1 != regEx.length()) {
                result = Character.toString(regEx.charAt(counter + 1));
                counter += 2;
            }
        }

        return result;
    }

    String caseChar() {
        String result;
        if ((counter + 1 != regEx.length()) && isCuantifier(regEx.charAt(counter + 1))) {
            result = getExCharCuantifier(regEx.charAt(counter + 1),Character.toString(regEx.charAt(counter)));
            counter += 2;
        } else {
            result = Character.toString(regEx.charAt(counter));
            counter++;
        }
        return result;
    }

    String caseDot() {
        String result;
        if ((counter + 1 != regEx.length()) && isCuantifier(regEx.charAt(counter + 1))) {
            result = getExDotCuantifier(regEx.charAt(counter + 1));
            counter += 2;
        } else {
            result = getExDot();
            counter++;
        }
        return result;
    }

    String caseCorchete() {
        StringBuilder corcheteContent = new StringBuilder();
        String result;
        counter++;
        while (regEx.charAt(counter) != ']') {
            corcheteContent.append(regEx.charAt(counter));
            counter++;
        }
        if ((counter + 1 != regEx.length()) && isCuantifier(regEx.charAt(counter + 1))) {
            result = getExCorcheteCuantifier(regEx.charAt(counter + 1),corcheteContent.toString());
            counter += 2;
        } else {
            result = getExCorchete(corcheteContent.toString());
            counter++;
        }
        return result;
    }

    String getExDot() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(CANT_CHAR);
        char randomIntToChar = (char) randomInt;
        return Character.toString(randomIntToChar);
    }

    String getExCorchete(String elements) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(elements.length());
        return Character.toString(elements.charAt(randomInt));
    }

    String getExCorcheteCuantifier(char cuantifier, String elements) {
        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < getNumRepOfCuantifier(cuantifier); i++) {
            finalString.append(getExCorchete(elements));
        }
        return finalString.toString();
    }

    String getExDotCuantifier(char cuantifier) {
        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < getNumRepOfCuantifier(cuantifier); i++) {
            finalString.append(getExDot());
        }
        return finalString.toString();
    }

    String getExCharCuantifier(char cuantifier,String element) {
        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < getNumRepOfCuantifier(cuantifier); i++) {
            finalString.append(element);
        }
        return finalString.toString();
    }

    int getNumRepOfCuantifier(char cuantifier) {
        int numOfRepOfCuantifier = 0;
        Random randomGenerator = new Random();
        if (cuantifier == '*') {
            numOfRepOfCuantifier = randomGenerator.nextInt(LONG_MAX);
        }
        if (cuantifier == '?') {
            numOfRepOfCuantifier = randomGenerator.nextInt(2);
        }
        if (cuantifier == '+') {
            numOfRepOfCuantifier = randomGenerator.nextInt(LONG_MAX - 1) + 1;
        }

        return numOfRepOfCuantifier;
    }


}