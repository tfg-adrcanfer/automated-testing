package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import java.util.Random;

import androidx.test.uiautomator.UiObject;
import esadrcanfer.us.alumno.autotesting.dictionary.DictionaryBasedValueGenerator;

public class TextInputGenerator extends InputGenerator {

    Integer seed;

    public TextInputGenerator(Integer seed){
        this.seed = seed;
    }

    public void generateInput(UiObject object) {
        try {
            DictionaryBasedValueGenerator dictionary = new DictionaryBasedValueGenerator(1, seed);
            object.setText(dictionary.generate().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
