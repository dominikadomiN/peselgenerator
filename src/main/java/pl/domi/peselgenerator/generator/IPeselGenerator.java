package pl.domi.peselgenerator.generator;

import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;
import pl.domi.peselgenerator.model.Month;

public interface IPeselGenerator {

    String generate(String year, Month month, String day, Gender gender) throws PeselGeneratorException;

}
