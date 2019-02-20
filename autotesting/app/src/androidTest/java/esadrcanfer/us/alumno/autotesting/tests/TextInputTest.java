package esadrcanfer.us.alumno.autotesting.tests;

import android.util.Log;

import net.sf.extjwnl.JWNLException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.DiversityRandomSearch;
import esadrcanfer.us.alumno.autotesting.algorithms.DiversityRandomSearch2;
import esadrcanfer.us.alumno.autotesting.algorithms.DynamicRandomSearch;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomSearch;
import esadrcanfer.us.alumno.autotesting.dictionary.DictionaryBasedValueGenerator;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraph;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraphBuilder;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.graph.ApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicTestExecutionTimeObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.graph.ObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class TextInputTest {

    static String appPackageName = "esadrcanfer.us.alumno.textinputapp";

    @Test
    public void testRandomSearch() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        INAGraph graph= INAGraphBuilder.getInstance().build(mDevice,appPackageName);
        ObjectiveFunction abruptShutdown=new ApplicationCrashObjectiveFunction();
        RandomSearch algorithm=new RandomSearch(abruptShutdown,10,2);
        TestCase testCase=algorithm.run(graph,appPackageName);
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        Log.d("TFG","Done!");
    }

    @Test
    public void testDynamicRandomSearch() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        DynamicApplicationCrashObjectiveFunction objective = new DynamicApplicationCrashObjectiveFunction();
        DynamicRandomSearch algorithm=new DynamicRandomSearch(objective, 10,2, appPackageName, false);
        TestCase testCase=algorithm.run(mDevice, appPackageName);
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        Log.d("TFG","Done!");
    }

    @Test
    public void testDynamicRandomSearchMaxTime() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        DynamicTestExecutionTimeObjectiveFunction objective = new DynamicTestExecutionTimeObjectiveFunction();
        DynamicRandomSearch algorithm=new DynamicRandomSearch(objective, 10,2, appPackageName, false);
        TestCase testCase=algorithm.run(mDevice, appPackageName);
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
        DiversityRandomSearch algorithm=new DiversityRandomSearch(10, 1,2, appPackageName, false);
        List<TestCase> testCases=algorithm.run(mDevice, appPackageName);
        Log.d("TFG","Test cases founded: " + testCases.size());
    }

    @Test
    public void testDiversityRandomSearch2() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        DiversityRandomSearch2 algorithm=new DiversityRandomSearch2(2, 2,1, appPackageName, false);
        List<TestCase> testCases=algorithm.run(mDevice, appPackageName);
        Log.d("TFG","Test cases founded: " + testCases.size());
    }

    @Test
    public void testCloseApp() throws JWNLException {
        DictionaryBasedValueGenerator dictionary = new DictionaryBasedValueGenerator(1, new Random(1).nextInt());
        String text = dictionary.generate().toString();
        Log.d("TFG", text);
    }

}
