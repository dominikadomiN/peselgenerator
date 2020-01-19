package pl.domi.peselgenerator.generator.controldigit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControlDigitGeneratorTest {

    private ControlDigitGenerator controlDigitGenerator = new ControlDigitGenerator();

    @Test
    public void shouldGenerateControlDigit() {
        //given, when
        int actual = controlDigitGenerator.generateControlDigit("89102454133");
        //then
        assertEquals(3, actual);
    }
}