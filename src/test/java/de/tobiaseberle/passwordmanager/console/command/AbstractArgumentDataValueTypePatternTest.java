package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.command.model.argument.ArgumentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractArgumentDataValueTypePatternTest {


    @Test
    void testStringPattern() {
        String stringPattern = ArgumentType.STRING.getPattern();

        Assertions.assertTrue("hello".matches(stringPattern));
        Assertions.assertTrue("12345".matches(stringPattern));
        Assertions.assertTrue("true".matches(stringPattern));
    }

    @Test
    void testIntegerPattern() {
        String integerPattern = ArgumentType.INTEGER.getPattern();

        Assertions.assertTrue("12345".matches(integerPattern));
        Assertions.assertTrue("0".matches(integerPattern));

        Assertions.assertFalse("-12345".matches(integerPattern));
        Assertions.assertFalse("12.34".matches(integerPattern));
        Assertions.assertFalse("abc123".matches(integerPattern));
    }

    @Test
    void testBooleanPattern() {
        String booleanPattern = ArgumentType.BOOLEAN.getPattern();

        Assertions.assertTrue("true".matches(booleanPattern));
        Assertions.assertTrue("false".matches(booleanPattern));
        Assertions.assertTrue("TRUE".matches(booleanPattern));
        Assertions.assertTrue("FALSE".matches(booleanPattern));
        Assertions.assertTrue("yes".matches(booleanPattern));
        Assertions.assertTrue("no".matches(booleanPattern));
        Assertions.assertTrue("YES".matches(booleanPattern));
        Assertions.assertTrue("NO".matches(booleanPattern));

        Assertions.assertFalse("abc".matches(booleanPattern));
        Assertions.assertFalse("123".matches(booleanPattern));
        Assertions.assertFalse("0".matches(booleanPattern));
        Assertions.assertFalse("1".matches(booleanPattern));
    }
}
