package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.widget.RadioButton;

import java.util.Random;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

public class RadioButtonInputGenerator extends InputGenerator {

    Integer seed;
    public RadioButtonInputGenerator(Integer seed){
        this.seed = seed;
    }

    public void generateInput(UiObject target) throws UiObjectNotFoundException {
        Integer selectedRadioButton = new Random(seed).nextInt(target.getChildCount());
        UiObject dataValue = target.getChild(new UiSelector().className(RadioButton.class.getName()).index(selectedRadioButton));
        dataValue.click();
    }
}
