package esadrcanfer.us.alumno.autotesting.tests;

import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomSearch;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraph;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraphBuilder;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class InputTextTest {


    @Test
    public void test1() throws UiObjectNotFoundException, InterruptedException {
        UiDevice mDevice;
        mDevice = UiDevice.getInstance(getInstrumentation());
        INAGraph graph= INAGraphBuilder.getInstance().build(mDevice,"esadrcanfer.us.alumno.textinputtest");
        ObjectiveFunction abruptShutdown=new ApplicationCrashObjectiveFunction();
        RandomSearch algorithm=new RandomSearch(abruptShutdown,10,2);
        TestCase testCase=algorithm.run(graph,"esadrcanfer.us.alumno.textinputtest");
        System.out.println("Test case found: "+testCase);
        System.out.println("Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        System.out.println("Done!");
    }


}
