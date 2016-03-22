package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator();
        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");

        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);

    }

    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 1));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
    }

    @Test
    public void testIsCuantifierPlass() {
        RegExGenerator generator = new RegExGenerator();
        assertTrue(generator.isCuantifier('+'));
    }
    @Test
    public void testIsCuantifierAsterisk() {
        RegExGenerator generator = new RegExGenerator();
        assertTrue(generator.isCuantifier('*'));
    }
    @Test
    public void testIsCuantifierQuestion() {
        RegExGenerator generator = new RegExGenerator();
        assertTrue(generator.isCuantifier('?'));
    }
    @Test
    public void testGetExDot() {
        RegExGenerator generator = new RegExGenerator();
        assertTrue((generator.getExDot()).length() == 1);
    }
    @Test
    public void testGetExCorchete() {
        RegExGenerator generator = new RegExGenerator();
        String result = generator.getExCorchete("ab");
        assertTrue(result.charAt(0) == 'a' || result.charAt(0) == 'b');
    }
    @Test
    public void testCuantifierAnswer() {
        RegExGenerator generator = new RegExGenerator();
        String result = generator.getExCharCuantifier('?',"a");
        assertTrue(result.length() == 0 || result.length() == 1);
    }
    @Test
    public void testCuantifierPlass() {
        RegExGenerator generator = new RegExGenerator();
        String result = generator.getExCharCuantifier('+',"a");
        assertTrue(result.length() >= 1 );
    }
    @Test
    public void testCuantifierAsterisk() {
        RegExGenerator generator = new RegExGenerator();
        String result = generator.getExCharCuantifier('+',"a");
        assertTrue(result.length() >= 0 );
    }
    @Test
    public void testgenerate() {
        RegExGenerator generator = new RegExGenerator();
        List<String> results = generator.generate("a", 1);
        assertTrue(results.get(0).charAt(0)=='a');
    }
}
