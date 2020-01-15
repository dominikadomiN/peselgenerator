package pl.domi.peselgenerator.generator;

import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;
import pl.domi.peselgenerator.model.Month;

import java.time.LocalDate;

public interface IPeselGenerator {

    String generate(LocalDate dateOfBirth, Gender gender) throws PeselGeneratorException;

}
