package esadrcanfer.us.alumno.autotesting.tests;

import org.junit.Test;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomSearch;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraph;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraphBuilder;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.graph.ObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.graph.TestExecutionTimeObjectiveFunction;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class RandomSearchForMaximizingExecutionTime {

    @Test
    public void test1() throws UiObjectNotFoundException, InterruptedException {
        UiDevice mDevice;
        mDevice = UiDevice.getInstance(getInstrumentation());
        INAGraph graph = INAGraphBuilder.getInstance().build(mDevice,"com.example.testingandroid2");
        ObjectiveFunction abruptShutdown = new TestExecutionTimeObjectiveFunction();
        RandomSearch algorithm = new RandomSearch(abruptShutdown, 100, 3);
        TestCase testCase = algorithm.run(graph, "com.example.testingandroid2");
        System.out.println("Test case found: " + testCase);
        System.out.println("Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        System.out.println("Done!");
    }
}
