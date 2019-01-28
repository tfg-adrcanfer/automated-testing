package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import java.util.Random;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

class CheckBoxInputGenerator extends InputGenerator {
    public void generateInput(UiObject object) throws UiObjectNotFoundException {

        if (new Random().nextBoolean()) {
            object.click();
        }
    }
}