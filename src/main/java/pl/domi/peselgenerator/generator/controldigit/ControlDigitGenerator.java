package pl.domi.peselgenerator.generator.controldigit;

public class ControlDigitGenerator {

    public int generateControlDigit(String pesel) {

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

        return sum % 10;
    }
}
