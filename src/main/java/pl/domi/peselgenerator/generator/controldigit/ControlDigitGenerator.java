package pl.domi.peselgenerator.generator.controldigit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;

public class ControlDigitGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlDigitGenerator.class);

    public static int generateControlDigit(String pesel) throws PeselGeneratorException {
        if (pesel.isEmpty()) {
            LOGGER.debug("PeselGeneratorException");
            throw new PeselGeneratorException();
        }

        int firstDigitCheck = 9 * Integer.parseInt(pesel.substring(0, 1));

        int secondDigitCheck = 7 * Integer.parseInt(pesel.substring(1, 2));
        int thirdDigitCheck = 3 * Integer.parseInt(pesel.substring(2, 3));
        int forthDigitCheck = Integer.parseInt(pesel.substring(3, 4));
        int fifthDigitCheck = 9 * Integer.parseInt(pesel.substring(4, 5));
        int sixthDigitCheck = 7 * Integer.parseInt(pesel.substring(5, 6));
        int seventhDigitCheck = 3 * Integer.parseInt(pesel.substring(6, 7));
        int eighthDigitCheck = Integer.parseInt(pesel.substring(7, 8));
        int ninthDigitCheck = 9 * Integer.parseInt(pesel.substring(8, 9));
        int tenthDigitCheck = 7 * Integer.parseInt(pesel.substring(9, 10));

        int sum = firstDigitCheck + secondDigitCheck + thirdDigitCheck + forthDigitCheck + fifthDigitCheck +
                sixthDigitCheck + seventhDigitCheck + eighthDigitCheck + ninthDigitCheck + tenthDigitCheck;

        LOGGER.info("Total sum of 10 digits {}", sum);
        return sum % 10;
    }
}
