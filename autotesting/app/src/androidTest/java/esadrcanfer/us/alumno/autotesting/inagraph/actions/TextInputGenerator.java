package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import esadrcanfer.us.alumno.autotesting.dictionary.DictionaryBasedValueGenerator;

public class TextInputGenerator extends InputGenerator {

    public void generateInput(UiObject object) {
        try {
            DictionaryBasedValueGenerator dictionary = new DictionaryBasedValueGenerator(1,1);
            object.setText(dictionary.generate().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
