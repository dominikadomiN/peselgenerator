package pl.domi.peselgenerator.generator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;

import java.time.LocalDate;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PeselGeneratorTest {

    private static Stream<Arguments> parametersForShouldGeneratePesel() {
        return Stream.of(
                Arguments.of(LocalDate.of(1987, 3, 7), Gender.WOMAN, "87", "03", "07", 0),
                Arguments.of(LocalDate.of(1875, 12, 24), Gender.MAN, "75", "92", "24", 1),
                Arguments.of(LocalDate.of(2012, 4, 14), Gender.MAN, "12", "24", "14", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForShouldGeneratePesel")
    void shouldGeneratePesel(LocalDate dateOfBirth,
                             Gender gender,
                             String expectedYear,
                             String expectedMonth,
                             String expectedDay,
                             int expectedGenderModule) throws PeselGeneratorException {
        // given
        var peselGenerator = new PeselGenerator();

        // when
        var actual = peselGenerator.generate(dateOfBirth, gender);
        var actualGenderModule = parseInt(actual.substring(9, 10)) % 2;

        //then
        assertAll(
                () -> assertEquals(expectedYear, actual.substring(0, 2)),
                () -> assertEquals(expectedMonth, actual.substring(2, 4)),
                () -> assertEquals(expectedDay, actual.substring(4, 6)),
                () -> assertEquals(expectedGenderModule, actualGenderModule)
        );
    }

    private static Stream<Arguments> parametersForShouldPeselGeneratorException_whenDateOfBirthOrGenderNull() {
        return Stream.of(
                Arguments.of(null, Gender.WOMAN),
                Arguments.of(LocalDate.now(), null),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForShouldPeselGeneratorException_whenDateOfBirthOrGenderNull")
    void shouldThrowsPeselGeneratorException(LocalDate givenDateOfBirth, Gender givenGender) {
        // given
        var peselGenerator = new PeselGenerator();

        //when, then
        assertThrows(PeselGeneratorException.class, () -> peselGenerator.generate(givenDateOfBirth, givenGender));
    }
}