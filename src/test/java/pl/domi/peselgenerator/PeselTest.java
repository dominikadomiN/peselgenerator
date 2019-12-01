package pl.domi.peselgenerator;

import org.junit.Test;
import pl.domi.peselgenerator.model.Gender;
import pl.domi.peselgenerator.model.Month;
import pl.domi.peselgenerator.model.Pesel;
import pl.domi.peselgenerator.validator.exception.PeselValidatorException;

import static org.junit.Assert.assertEquals;

public class PeselTest {

    @Test
    public void shouldReturnGenderWoman() {
        //given
        Pesel pesel = new Pesel("47080108480");
        Gender expected = Gender.WOMAN;
        //when
        Gender actual = pesel.getGender();
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnGenderMan() {
        //given
        Pesel pesel = new Pesel("64061707959");
        Gender expected = Gender.MAN;
        //when
        Gender actual = pesel.getGender();
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnControlNumber() {
        //given
        Pesel pesel = new Pesel("78060608319");
        //when
        int actual = pesel.getControlDigit();
        //then
        assertEquals(9, actual);
    }

    @Test
    public void shouldReturnMonthMarch() throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel("58030293282");
        //when
        Month actual = pesel.getMonth();
        //then
        assertEquals(Month.MARCH, actual);
    }

    @Test(expected = PeselValidatorException.class)
    public void shouldThrowException_WhenAskingForMonth() throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel("58130293282");
        //when
        pesel.getMonth();
    }

    @Test
    public void shouldReturnYearFromCorrectPesel() throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel("58030293282");
        String expected = "1958";
        //when
        String actual = pesel.getYear();
        //then
        assertEquals(expected, actual);
    }

    @Test(expected = PeselValidatorException.class)
    public void shouldThrowException_WhenAskingForYear() throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel("58610293282");
        //when
        pesel.getYear();
    }

    @Test
    public void shouldReturnYear1800() throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel("81905098291");
        //when
        String actual = pesel.getYear();
        //then
        assertEquals("1881", actual);
    }

    @Test
    public void shouldReturnYear1900() throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel("81050982911");
        //when
        String actual = pesel.getYear();
        //then
        assertEquals("1981", actual);
    }

    @Test
    public void shouldReturnYear2000() throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel("01231986237");
        //when
        String actual = pesel.getYear();
        //then
        assertEquals("2001", actual);
    }
}
