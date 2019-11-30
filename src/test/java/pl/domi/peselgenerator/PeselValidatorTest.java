package pl.domi.peselgenerator;

import org.junit.Test;
import pl.domi.peselgenerator.model.Gender;
import pl.domi.peselgenerator.validator.PeselValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PeselValidatorTest {
    private PeselValidator peselValidator = new PeselValidator();

    @Test
    public void verifyingPesel_withCorrectPesel() {
        //when
        boolean actual = peselValidator.isValid("67050402916");
        //then
        assertTrue(actual);
    }

    @Test
    public void verifyingPesel_withShorterInvalidPesel() {
        //when
        boolean actual = peselValidator.isValid("670504029");
        //then
        assertFalse(actual);
    }

    @Test
    public void verifyingPesel_withNullPesel() {
        //when
        boolean actual = peselValidator.isValid(null);
        //then
        assertFalse(actual);
    }

    @Test
    public void verifyingPesel_withEmptyPesel() {
        //when
        boolean actual = peselValidator.isValid("");
        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnGenderWoman() {
        //given
        Gender expected = Gender.WOMAN;
        //when
        Gender actual = peselValidator.getGender("47080108480");
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnGenderMan() {
        //given
        Gender expected = Gender.MAN;
        //when
        Gender actual = peselValidator.getGender("64061707959");
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnControlNumber() {
        //when
        int actual = peselValidator.getControlDigit("78060608319");
        //then
        assertEquals(9, actual);
    }

    @Test
    public void isValid_shouldReturnTrueWithCorrectPesel() {
        //when
        boolean actual = peselValidator.isValid("44051401359");
        //then
        assertTrue(actual);
    }

    @Test
    public void isValid_shouldReturnFalseWithWrongPesel() {
        //when
        boolean actual = peselValidator.isValid("44051401358");
        //then
        assertFalse(actual);
    }
}
