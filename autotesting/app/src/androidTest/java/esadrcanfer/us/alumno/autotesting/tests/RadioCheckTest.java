package esadrcanfer.us.alumno.autotesting.tests;

import android.widget.CheckBox;
import android.widget.RadioButton;

import org.junit.Test;

import java.util.List;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.TestUtilities;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomSearch;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraph;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraphBuilder;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.CheckBoxAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ElementIdentifier;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonInputGenerator;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class RadioCheckTest {
    
    @Test
    public void test1() throws UiObjectNotFoundException {
        UiDevice mDevice;
        mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();
        TestUtilities.OpenApp("WidgetsApp", true);
        for (UiObject checkBox: TestUtilities.findCheckBox(mDevice)){
            CheckBoxAction checkBoxAction = new CheckBoxAction(checkBox);
            checkBoxAction.perform();
        }

        List<UiObject> rButtons = ElementIdentifier.findElements(mDevice, "android.widget.RadioButton");
        for (int i = 0; i < 10; i++){
            for (UiObject rbutton: rButtons){
                RadioButtonInputGenerator radioButtonInputGenerator = new RadioButtonInputGenerator();
                RadioButtonAction radioButtonAction = new RadioButtonAction(rbutton, radioButtonInputGenerator);
                radioButtonAction.perform();
            }
        }


    }

}
