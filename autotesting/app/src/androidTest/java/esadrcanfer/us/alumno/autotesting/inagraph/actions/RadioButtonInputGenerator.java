package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.widget.RadioButton;

import java.util.Random;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

public class RadioButtonInputGenerator extends InputGenerator {

    Random random;
    public RadioButtonInputGenerator(Random random){
        this.random = random;
    }

    public void generateInput(UiObject target) throws UiObjectNotFoundException {
        Integer selectedRadioButton = random.nextInt(target.getChildCount());
        UiObject dataValue = target.getChild(new UiSelector().className(RadioButton.class.getName()).index(selectedRadioButton));
        dataValue.click();
    }
}
