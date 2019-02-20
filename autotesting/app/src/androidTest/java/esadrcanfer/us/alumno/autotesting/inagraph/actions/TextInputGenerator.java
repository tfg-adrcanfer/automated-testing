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

    public void generateInput(UiObject object) {
        try {
            DictionaryBasedValueGenerator dictionary = new DictionaryBasedValueGenerator(1, seed);
            String text = dictionary.generate().toString();
            Log.d("TFG", text);
            object.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
