package pl.domi.peselgenerator.validator;

import pl.domi.peselgenerator.model.Gender;

public interface IPeselValidator {

    Gender getGender(String pesel);

    boolean isValid(String pesel);

    int getControlDigit(String pesel);

}
