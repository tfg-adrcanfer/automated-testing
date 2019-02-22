package esadrcanfer.us.alumno.autotesting.tests;

import android.util.Log;

import org.junit.Test;

import java.util.List;
import java.util.Random;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.DiversityRandomSearch;
import esadrcanfer.us.alumno.autotesting.algorithms.DiversityRandomSearch2;
import esadrcanfer.us.alumno.autotesting.algorithms.DynamicRandomSearch;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomSearch;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraph;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraphBuilder;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.CheckBoxAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ElementIdentifier;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonInputGenerator;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.graph.ApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.graph.ObjectiveFunction;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class DiversityTest {

    String appPackage = "esadrcanfer.us.diversityapp";

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
    public void testDynamicRandomSearch() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        DynamicApplicationCrashObjectiveFunction objective = new DynamicApplicationCrashObjectiveFunction();
        DynamicRandomSearch algorithm=new DynamicRandomSearch(objective,5,3, appPackage, true);
        TestCase testCase=algorithm.run(mDevice, appPackage);
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        Log.d("TFG","Done!");
    }

    @Test
    public void testDiversityRandomSearch() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        DiversityRandomSearch2 algorithm=new DiversityRandomSearch2(10, 3,4, appPackage, false);
        List<TestCase> testCases=algorithm.run(mDevice, appPackage);
        Log.d("TFG","Test cases founded: " + testCases.size());
    }

    @Test
    public void testDiversityRandomSearch2() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        DiversityRandomSearch2 algorithm=new DiversityRandomSearch2(30, 10,6, appPackage, false);
        List<TestCase> testCases=algorithm.run(mDevice, appPackage);
        Log.d("TFG","Test cases founded: " + testCases);
    }
}
