package pl.domi.peselgenerator.generator.controldigit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControlDigitGeneratorTest {

    @Test
    void shouldGenerateControlDigit() {
        //given
        ControlDigitGenerator controlDigitGenerator = new ControlDigitGenerator();

        // when
        int actual = controlDigitGenerator.generateControlDigit("89102454133");

        //then
        assertEquals(3, actual);
    }
}