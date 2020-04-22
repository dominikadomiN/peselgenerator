package pl.domi.peselgenerator.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.domi.peselgenerator.validator.exception.PeselValidatorException;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PeselTest {

    private static Stream<Arguments> parametersForVerifyingGender() {
        return Stream.of(
                Arguments.of("47080108480", Gender.WOMAN),
                Arguments.of("64061707959", Gender.MAN)
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForVerifyingGender")
    void shouldReturnGender(String peselValue, Gender expectedGender) throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel(peselValue);

        //when
        Gender actual = pesel.getGender();

        //then
        assertEquals(expectedGender, actual);

    }

    private static Stream<Arguments> parametersForVerifyingYear() {
        return Stream.of(
                Arguments.of("81901098291", LocalDate.of(1881, 10, 10)), //10-10-1881
                Arguments.of("81050982911", LocalDate.of(1981, 5, 9)), //09-05-1981
                Arguments.of("01231986237", LocalDate.of(2001, 3, 19)) //19-03-2001
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForVerifyingYear")
    void shouldReturnDateOfBirth(String peselValue,
                                 LocalDate expectedDateOfBirth) throws PeselValidatorException {
        //given
        Pesel pesel = new Pesel(peselValue);

        //when
        LocalDate actual = pesel.getDateOfBirth();

        //then
        assertEquals(expectedDateOfBirth, actual);

    }

    private static Stream<String> parametersForInvalidPesel() {
        return Stream.of(
                null,             // pesel is null
                "960311",         // pesel too short
                "7806230859631",  // pesel too long
                "fslklklklkl",    // all invalid characters
                "940325alaaa",    // invalid characters
                "81501098299",    // invalid year-month
                "47180108483",    // invalid month
                "87025689744",    // invalid day
                "01222956892",    // invalid day for not leap year
                "01231986231"     // invalid control digit
        );
    }


    @ParameterizedTest
    @MethodSource("parametersForInvalidPesel")
    void shouldThrowException_whenInvalidValueForPesel(String peselValue) {
        //given,when
        assertThrows(PeselValidatorException.class, () -> new Pesel(peselValue));
    }
}
