package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import java.util.Random;

import androidx.test.uiautomator.UiObject;
import esadrcanfer.us.alumno.autotesting.dictionary.DictionaryBasedValueGenerator;

public class TextInputGenerator extends InputGenerator {

    Integer seed;

    public TextInputGenerator(Integer seed){
        this.seed = seed;
    }

    public String generateInput(UiObject object) {
        String value = null;
        try {
            DictionaryBasedValueGenerator dictionary = new DictionaryBasedValueGenerator(1, seed);
            value = dictionary.generate().toString();
            Log.d("TFG", value);
            object.setText(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }



}
