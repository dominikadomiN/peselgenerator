package pl.domi.peselgenerator.generator;

import org.junit.Before;
import org.junit.Test;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;
import pl.domi.peselgenerator.model.Month;
import pl.domi.peselgenerator.validator.IPeselValidator;
import pl.domi.peselgenerator.validator.PeselValidator;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PeselGeneratorTest {

    private PeselGenerator peselGenerator;
    private IPeselValidator peselValidatorMock;

    @Before
    public void setUp() {
        this.peselValidatorMock = mock(PeselValidator.class);
        this.peselGenerator = new PeselGenerator(peselValidatorMock);
    }

    @Test
    public void shouldGeneratePeselForWomanWith1987() throws PeselGeneratorException {
        //given
        when(peselValidatorMock.isValid(anyString())).thenReturn(true);

        // when
        String actual = peselGenerator.generate("1987", Month.MARCH, "07", Gender.WOMAN);
        int actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertEquals("87", actual.substring(0, 2));
        assertEquals("03", actual.substring(2, 4));
        assertEquals("07", actual.substring(4, 6));
        assertEquals(0, actualGenderModule);

        verify(peselValidatorMock).isValid(anyString());
    }

    @Test
    public void shouldGeneratePeselForManWith1875() throws PeselGeneratorException {
        //given
        when(peselValidatorMock.isValid(anyString())).thenReturn(true);

        // when
        String actual = peselGenerator.generate("1875", Month.DECEMBER, "24", Gender.MAN);
        int actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertEquals("75", actual.substring(0, 2));
        assertEquals("92", actual.substring(2, 4));
        assertEquals("24", actual.substring(4, 6));
        assertEquals(1, actualGenderModule);

        verify(peselValidatorMock).isValid(anyString());
    }

    @Test
    public void shouldGeneratePeselForManWith2012() throws PeselGeneratorException {
        //given
        when(peselValidatorMock.isValid(anyString())).thenReturn(true);

        // when
        String actual = peselGenerator.generate("2012", Month.APRIL, "14", Gender.MAN);
        int actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertEquals("12", actual.substring(0, 2));
        assertEquals("24", actual.substring(2, 4));
        assertEquals("14", actual.substring(4, 6));
        assertEquals(1, actualGenderModule);

        verify(peselValidatorMock).isValid(anyString());
    }
}