package esadrcanfer.us.alumno.autotesting.dictionary;

import java.util.Random;

public interface RandomizedGenerator extends ParameterValueGenerator {
    public Random getRandomGenerator();
    public void setRandomGenerator(Random r);
}
