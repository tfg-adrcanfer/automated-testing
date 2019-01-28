package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.widget.RadioButton;

import java.util.Random;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

public class RadioButtonInputGenerator extends InputGenerator {

    public void generateInput(UiObject object) throws UiObjectNotFoundException {
        if (!object.getClassName().toLowerCase().contains("RadioGroup")) {
            throw new IllegalArgumentException("Tried to generate a Radio Button Action on a element that is not a RadioGroup: " +
                    object.getClassName());
        }

        Integer childToclick = new Random().nextInt(object.getChildCount() - 1);
        UiObject dataValue = object.getChild(new UiSelector().className(RadioButton.class.getName()).index(childToclick));
        dataValue.click();
    }
}
