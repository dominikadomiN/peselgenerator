package pl.domi.peselgenerator.generator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import pl.domi.peselgenerator.generator.controldigit.ControlDigitGenerator;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class PeselGeneratorTest {

    private PeselGenerator peselGenerator;
    private ControlDigitGenerator controlDigitGeneratorMock;

    @Before
    public void setUp() {
        this.controlDigitGeneratorMock = Mockito.mock(ControlDigitGenerator.class);
        this.peselGenerator = new PeselGenerator(controlDigitGeneratorMock);
    }

    private Object[][] parametersForShouldGeneratePesel() {
        return new Object[][]{
                {LocalDate.of(1987, 3, 7), Gender.WOMAN, "87", "03", "07", 0},
                {LocalDate.of(1875, 12, 24), Gender.MAN, "75", "92", "24", 1},
                {LocalDate.of(2012, 4, 14), Gender.MAN, "12", "24", "14", 1}
        };
    }

    @Test
    @Parameters(method = "parametersForShouldGeneratePesel")
    public void shouldGeneratePesel(LocalDate dateOfBirth,
                                    Gender gender,
                                    String expectedYear,
                                    String expectedMonth,
                                    String expectedDay,
                                    int expectedGenderModule) throws PeselGeneratorException {
        //given
        when(controlDigitGeneratorMock.generateControlDigit(anyString())).thenReturn(3);

        // when
        String actual = peselGenerator.generate(dateOfBirth, gender);
        int actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertEquals(expectedYear, actual.substring(0, 2));
        assertEquals(expectedMonth, actual.substring(2, 4));
        assertEquals(expectedDay, actual.substring(4, 6));
        assertEquals(expectedGenderModule, actualGenderModule);

        verify(controlDigitGeneratorMock).generateControlDigit(anyString());
    }
}