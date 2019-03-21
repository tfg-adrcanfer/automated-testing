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
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparationSearch;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicApplicationCrashObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicTestExecutionTimeObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class AutomaticRepairTests {

    @Test
    public void testCaseExecution() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("TestCase-20190321_211219_bug.txt", true);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        testCase.executeBefore();
        Boolean eval = testCase.executeTest();
        if (eval == false){
            RandomReparationSearch randomReparationSearch = new RandomReparationSearch(5, testCase, -732439767l, testCase.getAppPackage());
            randomReparationSearch.run(device, testCase.getAppPackage());
        }
        testCase.executeAfter();
        Log.d("ISA", "Eval: " + eval.toString());
    }

    @Test
    public void generateTestCase() throws UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation());
        createTestCase();
    }

    public static TestCase createTestCase() throws UiObjectNotFoundException {
        Log.d("ISA", "Building Test case...");
        String appPackage = "esadrcanfer.us.mynotes";
        Random randomSeed = new Random();
        int seed = randomSeed.nextInt();
        Random random = new Random(seed);
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();;
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextInt());
        testActions.add(createNoteButton);
        Action addNoteText = ReadUtil.generateActionFromSimpleString("TEXT, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editText1]", random.nextInt());
        testActions.add(addNoteText);
        Action saveNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextInt());
        testActions.add(saveNoteButton);
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET,beforeActions,testActions,afterActions, new ArrayList<>());
        testCase.executeBefore();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.contains(testActions[1].value)");
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.write(appPackage);
        writerUtil.write(String.valueOf(seed));
        writerUtil.write(String.valueOf(testActions.size()));
        for (Action action:testActions) {
            writerUtil.write(action.toString());
        }
        writerUtil.write(testCase.getPredicate());
        writerUtil.write(finalState.toString());
        return testCase;
    }

    public static List<String> labelsDetection() throws UiObjectNotFoundException {
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
