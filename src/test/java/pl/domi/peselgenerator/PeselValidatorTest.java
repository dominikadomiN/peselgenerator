package pl.domi.peselgenerator;

import org.junit.Test;
import pl.domi.peselgenerator.validator.PeselValidator;

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
