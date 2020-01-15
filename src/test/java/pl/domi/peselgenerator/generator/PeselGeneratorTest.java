package pl.domi.peselgenerator.generator;

import org.junit.Test;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;

public class PeselGeneratorTest {

    private PeselGenerator peselGenerator = new PeselGenerator();

    @Test
    public void shouldGeneratePeselForWomanWith1987() throws PeselGeneratorException {
        //given
        LocalDate dateOfBirth = LocalDate.of(1987, 3, 7);

        // when
        String actual = peselGenerator.generate(dateOfBirth, Gender.WOMAN);
        int actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertEquals("87", actual.substring(0, 2));
        assertEquals("03", actual.substring(2, 4));
        assertEquals("07", actual.substring(4, 6));
        assertEquals(0, actualGenderModule);
    }

    @Test
    public void shouldGeneratePeselForManWith1875() throws PeselGeneratorException {
        //given
        LocalDate dateOfBirth = LocalDate.of(1875, 12, 24);

        // when
        String actual = peselGenerator.generate(dateOfBirth, Gender.MAN);
        int actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertEquals("75", actual.substring(0, 2));
        assertEquals("92", actual.substring(2, 4));
        assertEquals("24", actual.substring(4, 6));
        assertEquals(1, actualGenderModule);
    }

    @Test
    public void shouldGeneratePeselForManWith2012() throws PeselGeneratorException {
        //given
        LocalDate dateOfBirth = LocalDate.of(2012, 4, 14);

        // when
        String actual = peselGenerator.generate(dateOfBirth, Gender.MAN);
        int actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertEquals("12", actual.substring(0, 2));
        assertEquals("24", actual.substring(2, 4));
        assertEquals("14", actual.substring(4, 6));
        assertEquals(1, actualGenderModule);
    }
}