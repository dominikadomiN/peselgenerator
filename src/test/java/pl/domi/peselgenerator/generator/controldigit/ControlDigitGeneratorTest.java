package pl.domi.peselgenerator.generator.controldigit;

import org.junit.jupiter.api.Test;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControlDigitGeneratorTest {

    @Test
    void shouldGenerateControlDigit() throws PeselGeneratorException {
        //given
        var controlDigitGenerator = new ControlDigitGenerator();

        // when
        var actual = ControlDigitGenerator.generateControlDigit("89102454133");

        //then
        assertEquals(3, actual);
    }
}