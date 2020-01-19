package pl.domi.peselgenerator.validator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import pl.domi.peselgenerator.generator.controldigit.ControlDigitGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class PeselValidatorTest {
    private PeselValidator peselValidator;
    private ControlDigitGenerator controlDigitGeneratorMock;

    @Before
    public void setUp() {
        this.controlDigitGeneratorMock = Mockito.mock(ControlDigitGenerator.class);
        this.peselValidator = new PeselValidator(controlDigitGeneratorMock);
    }

    private Object[][] parametersForVerifyingPesel() {
        return new Object[][]{
                {"67050402916", 6, true},
                {"44051401358", 0, false}
        };
    }

    private Object[][] parametersForVerifyingInvalidPesel() {
        return new Object[][]{
                {"670504029"},
                {"null"},
                {""}
        };
    }

    @Test
    @Parameters(method = "parametersForVerifyingPesel")
    public void verifyingPesel_withCorrectPesel(String pesel,
                                                int generatedControlDigit,
                                                boolean expectedResult) {
        //given
        when(controlDigitGeneratorMock.generateControlDigit(pesel)).thenReturn(generatedControlDigit);
        //when
        boolean actual = peselValidator.isValid(pesel);
        //then
        assertEquals(expectedResult, actual);
        verify(controlDigitGeneratorMock).generateControlDigit(pesel);
    }

    @Test
    @Parameters(method = "parametersForVerifyingInvalidPesel")
    public void verifyingPesel_withIncorrectValue(String pesel) {
        //given
        when(controlDigitGeneratorMock.generateControlDigit(pesel)).thenReturn(0);
        //when
        boolean actual = peselValidator.isValid(pesel);
        //then
        assertFalse(actual);
        verify(controlDigitGeneratorMock, never()).generateControlDigit(pesel);
    }

}
