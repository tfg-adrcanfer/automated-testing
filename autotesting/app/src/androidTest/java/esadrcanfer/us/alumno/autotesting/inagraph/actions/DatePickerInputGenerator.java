package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.util.RandomUtils;

public class DatePickerInputGenerator extends InputGenerator {
    public void generateInput(UiObject object) {
        String text = RandomUtils.randomText(8, "0123456789"); //8 = standard Date size: ddMMYYYY
        try {
            object.setText(text);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }


}
