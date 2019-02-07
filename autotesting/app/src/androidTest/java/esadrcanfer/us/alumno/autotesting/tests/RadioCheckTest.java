package esadrcanfer.us.alumno.autotesting.tests;

import android.util.Log;

import org.junit.Test;

import java.util.List;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.DinamicRandomSearch;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomSearch;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraph;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraphBuilder;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ActionFactory;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.CheckBoxAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ElementIdentifier;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonInputGenerator;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class RadioCheckTest {

    String appPackage = "esadrcanfer.us.alumno";

    @Test
    public void test1() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        INAGraph graph= INAGraphBuilder.getInstance().build(mDevice,appPackage);
        Log.i("TFG",graph.getAvailableActions().toString());
        ObjectiveFunction abruptShutdown=new ApplicationCrashObjectiveFunction();
        RandomSearch algorithm=new RandomSearch(abruptShutdown,10,3);
        TestCase testCase=algorithm.run(graph,appPackage);
        Log.i("TFG","Test case found: "+testCase);
        Log.i("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        Log.i("TFG","Done!");
    }

    @Test
    public void testDinamicRandomSearch() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        DinamicRandomSearch algorithm=new DinamicRandomSearch(10,3, appPackage);
        TestCase testCase=algorithm.run(mDevice, appPackage);
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        Log.d("TFG","Done!");
    }

    @Test
    public void test2() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        List<UiObject> radioButtons = ElementIdentifier.findElements(mDevice, "android.widget.RadioGroup");
        for (UiObject radioButton: radioButtons){
            RadioButtonInputGenerator radioButtonInputGenerator = new RadioButtonInputGenerator();
            RadioButtonAction radioButtonAction = new RadioButtonAction(radioButton,radioButtonInputGenerator);
            radioButtonAction.perform();
        }
        List<UiObject> checkboxes = ElementIdentifier.findElements(mDevice, "android.widget.CheckBox");
        for (UiObject checkbox: checkboxes){
            CheckBoxAction checkBoxAction = new CheckBoxAction(checkbox);
            checkBoxAction.perform();
        }
        List<UiObject> buttons = ElementIdentifier.findElements(mDevice, "android.widget.Button");
        for (UiObject button: buttons){
            ButtonAction buttonAction = new ButtonAction(button);
            buttonAction.perform();
        }
    }

}
