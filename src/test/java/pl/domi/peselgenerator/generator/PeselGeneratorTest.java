package pl.domi.peselgenerator.generator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class PeselGeneratorTest {

    private PeselGenerator peselGenerator = new PeselGenerator();

    private Object[][] parametersForShouldGeneratePesel() {
        return new Object[][]{
                {LocalDate.of(1987, 3, 7), Gender.WOMAN, "87", "03", "07", 0},
                {LocalDate.of(1875, 12, 24), Gender.MAN, "75", "92", "24", 1},
                {LocalDate.of(2012, 4, 14), Gender.MAN, "12", "24", "14", 1}
        };
    }

    @Test
    @Parameters (method = "parametersForShouldGeneratePesel")
    public void shouldGeneratePesel(LocalDate dateOfBirth,
                                    Gender gender,
                                    String expectedYear,
                                    String expectedMonth,
                                    String expectedDay,
                                    int expectedGenderModule) throws PeselGeneratorException {
        // given,when
        String actual = peselGenerator.generate(dateOfBirth, gender);
        int actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertEquals(expectedYear, actual.substring(0, 2));
        assertEquals(expectedMonth, actual.substring(2, 4));
        assertEquals(expectedDay, actual.substring(4, 6));
        assertEquals(expectedGenderModule, actualGenderModule);
    }
}