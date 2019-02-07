package esadrcanfer.us.alumno.autotesting.tests;

import android.util.Log;

import org.junit.Test;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.DinamicRandomSearch;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomSearch;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraph;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraphBuilder;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.util.Writer;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class Button2Test {

    static String appPackageName = "com.example.testingandroid2";

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
    public void testDinamicRandomSearch() throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        ObjectiveFunction abruptShutdown=new ApplicationCrashObjectiveFunction();
        DinamicRandomSearch algorithm=new DinamicRandomSearch(10,2, appPackageName);
        TestCase testCase=algorithm.run(mDevice, appPackageName);
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        Log.d("TFG","Done!");
    }

    @Test
    public void testCloseApp() {
        Writer writer = new Writer();
        writer.write("Hola");
        writer.write("¿Cómo estás?");
        writer.write("Adiós");
        Log.d("TFG",writer.getPath());
    }

}
