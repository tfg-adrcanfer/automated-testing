package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import java.util.Random;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.util.RandomUtils;

public class TextInputGenerator extends InputGenerator {

    public void generateInput(UiObject object) {
        //for the moment we'll generate random texts of random length.
        //String text = RandomUtils.randomText(new Random().nextInt());
        try {
            object.setText("Prueba");
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }



}
