package esadrcanfer.us.alumno.autotesting.tests;

import android.util.Log;
import android.widget.TextView;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.DynamicRandomSearch;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicTestExecutionTimeObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class AutomaticRepairTests {

    //Template for test DynamicRandomSearch algorithm
    private void dynamicRandomSearchTestTemplate(String appPackageName, DynamicObjectiveFunction objective,
                                                 Integer iterations, Integer actionsLength,
                                                 Boolean saveAllTestCases) throws UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        DynamicRandomSearch algorithm = new DynamicRandomSearch(objective, iterations, actionsLength, appPackageName, saveAllTestCases);
        TestCase testCase = algorithm.run(mDevice, appPackageName);
        Log.d("TFG", "Test case found: " + testCase);
        Log.d("TFG", "Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        Log.d("TFG", "Done!");
    }

    //This test use DynamicRandomSearch algorithm with DynamicApplicationCrashObjetiveFunction into ButtomApp 1
    @Test
    public void testButtomApp1Crash() throws UiObjectNotFoundException {
        String appPackageName = "esadrcanfer.us.mynotes";
        DynamicObjectiveFunction objective = new DynamicApplicationCrashObjectiveFunction();
        Integer iterations = 1;
        Integer actionLength = 8;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }

    //This test use DynamicRandomSearch algorithm with DynamicMaxExecutionTimeObjetiveFunction into ButtomApp 1
    @Test
    public void testButtomApp1MaxExecutionTime() throws UiObjectNotFoundException {
        String appPackageName = "com.example.testingandroid";
        DynamicObjectiveFunction objective = new DynamicTestExecutionTimeObjectiveFunction(1000);
        Integer iterations = 40;
        Integer actionLength = 2;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);

    }

    //This test use DynamicRandomSearch algorithm with DynamicApplicationCrashObjetiveFunction into ButtomApp 2
    @Test
    public void testButtomApp2Crash() throws UiObjectNotFoundException {
        String appPackageName = "com.example.testingandroid2";
        DynamicObjectiveFunction objective = new DynamicApplicationCrashObjectiveFunction();
        Integer iterations = 10;
        Integer actionLength = 2;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }

    //This test use DynamicRandomSearch algorithm with DynamicMaxExecutionTimeObjetiveFunction into ButtomApp 2
    @Test
    public void testButtomApp2MaxExecutionTime() throws UiObjectNotFoundException {
        String appPackageName = "com.example.testingandroid2";
        DynamicObjectiveFunction objective = new DynamicTestExecutionTimeObjectiveFunction(3000);
        Integer iterations = 10;
        Integer actionLength = 2;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }

    //This test use DynamicRandomSearch algorithm with DynamicApplicationCrashObjetiveFunction into TextInputApp
    @Test
    public void testTextInputAppCrash() throws UiObjectNotFoundException {
        String appPackageName = "esadrcanfer.us.alumno.textinputapp";
        DynamicObjectiveFunction objective = new DynamicApplicationCrashObjectiveFunction();
        Integer iterations = 1;
        Integer actionLength = 4;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }

    //This test use DynamicRandomSearch algorithm with DynamicMaxExecutionTimeObjetiveFunction into TextInputApp
    @Test
    public void testTextInputAppMaxExecutionTime() throws UiObjectNotFoundException {
        String appPackageName = "esadrcanfer.us.alumno.textinputapp";
        DynamicObjectiveFunction objective = new DynamicTestExecutionTimeObjectiveFunction(3000);
        Integer iterations = 10;
        Integer actionLength = 2;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }

    //This test use DynamicRandomSearch algorithm with DynamicApplicationCrashObjetiveFunction into WidgetApp
    @Test
    public void testWidgetAppCrash() throws UiObjectNotFoundException {
        String appPackageName = "esadrcanfer.us.alumno";
        DynamicObjectiveFunction objective = new DynamicApplicationCrashObjectiveFunction();
        Integer iterations = 10;
        Integer actionLength = 2;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }

    //This test use DynamicRandomSearch algorithm with DynamicMaxExecutionTimeObjetiveFunction into WidgetApp
    @Test
    public void testWidgetAppMaxExecutionTime() throws UiObjectNotFoundException {
        String appPackageName = "esadrcanfer.us.alumno";
        DynamicObjectiveFunction objective = new DynamicTestExecutionTimeObjectiveFunction(3000);
        Integer iterations = 10;
        Integer actionLength = 2;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }

    //This test use DynamicRandomSearch algorithm with DynamicApplicationCrashObjetiveFunction into DiversityApp
    @Test
    public void testDiversityAppCrash() throws UiObjectNotFoundException {
        String appPackageName = "esadrcanfer.us.diversityapp";
        DynamicObjectiveFunction objective = new DynamicApplicationCrashObjectiveFunction();
        Integer iterations = 10;
        Integer actionLength = 2;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }

    //This test use DynamicRandomSearch algorithm with DynamicMaxExecutionTimeObjetiveFunction into DiversityApp
    @Test
    public void testDiversityMaxExecutionTime() throws UiObjectNotFoundException {
        String appPackageName = "esadrcanfer.us.diversityapp";
        DynamicObjectiveFunction objective = new DynamicTestExecutionTimeObjectiveFunction(3000);
        Integer iterations = 10;
        Integer actionLength = 2;
        Boolean saveAllTestCases = false;
        dynamicRandomSearchTestTemplate(appPackageName, objective, iterations, actionLength, saveAllTestCases);
    }



    @Test
    public void testCaseExecution() throws UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation());
        TestCase testCase = createTestCase();
        Log.d("ISA", "Test case created: " + testCase);
        Log.d("ISA", "Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.contains(testActions[1].value)");
        //Aquí tenemos un TestCase con su lista de labels al final de la ejecución
        Log.d("ISA", "Done!");
        testCase.executeBefore();
        Boolean eval = testCase.executeTest();
        testCase.executeAfter();
        Log.d("ISA", "Eval: " + eval.toString());
    }

    public static TestCase createTestCase(){
        String appPackage = "esadrcanfer.us.mynotes";
        Random random = new Random();
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();;
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextInt());
        testActions.add(createNoteButton);
        Action addNoteText = ReadUtil.generateActionFromString("TEXT, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editText1]", random.nextInt());
        testActions.add(addNoteText);
        Action saveNoteButton = ReadUtil.generateActionFromString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextInt());
        testActions.add(saveNoteButton);
        return new TestCase(appPackage, Collections.EMPTY_SET,beforeActions,testActions,afterActions, new ArrayList<>());
    }

    public List<String> labelsDetection() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        List<String> finalState = new ArrayList<>();
        List<UiObject2> elements = device.findObjects(By.clazz(TextView.class));
        Log.d("ISA", "Size: " + elements.size());
        for (UiObject2 label : elements) {
            String text = label.getText();
            //Solución básica, hay que mejorarla
            if(!(text.contains(":") || text.contains("%"))) {
                finalState.add(text);
                Log.d("ISA", "Label: " + label.getText());
            }
        }
        return finalState;
    }
}
