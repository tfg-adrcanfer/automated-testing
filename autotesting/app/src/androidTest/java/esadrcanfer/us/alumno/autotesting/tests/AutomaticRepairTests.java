package esadrcanfer.us.alumno.autotesting.tests;

import android.util.Log;
import android.widget.TextView;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.GRASPReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.RecycleReparation;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class AutomaticRepairTests {


    @Test
    public void testRandomReparation() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("EditionTestCase.txt", false);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            Log.d("ISA", "Initial evaluation: " + eval.toString());
            testCase.executeAfter();
        } catch (BrokenTestCaseException ex) {
            RandomReparation randomReparation = new RandomReparation(5, testCase, testCase.getAppPackage());
            testCase = randomReparation.run(device, testCase.getAppPackage());
        }
        Log.d("ISA", "TestCase found: " + testCase);
    }

    @Test
    public void testRecycleReparation() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("TestCase-create.txt", false);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            Log.d("ISA", "Initial evaluation: " + eval.toString());
            testCase.executeAfter();
        } catch (BrokenTestCaseException ex) {
            testCase.executeAfter();
            RecycleReparation recycleReparation = new RecycleReparation(5, testCase, (int) ex.getBreakingIndex());
            testCase = recycleReparation.run(device);
        }
        Log.d("ISA", "TestCase found: " + testCase);
    }


    @Test
    public void testGRASPReparation() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("Broken-CreationTestCase.txt", false);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            testCase.executeAfter();
        } catch (BrokenTestCaseException ex) {
            testCase.executeAfter();
            Log.d("ISA", "The test case is broken at step " + ex.getBreakingIndex() + "with message '" + ex.getMessage() + "'");
            Log.d("ISA", "Repairing it...");
            /*RandomReparationSearch randomReparationSearch = new RandomReparationSearch(5, testCase, testCase.getAppPackage());
            testCase = randomReparationSearch.run(device, testCase.getAppPackage());*/
            GRASPReparation grasp = new GRASPReparation(5, 3, 0);
            testCase = grasp.repair(device, testCase, (int) ex.getBreakingIndex());
            Log.d("ISA", "Repaired testCase found: " + testCase);
            Log.d("ISA", "Repaired testCase evaluated to: " + testCase.evaluate());
        }
    }

    @Test
    public void generateTestCases() throws UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation());
        //createTestCase();
        //editTestCase();
        //deleteTestCase();
    }

    public static TestCase createTestCase() throws UiObjectNotFoundException {
        Log.d("ISA", "Building Test case...");
        String appPackage = "esadrcanfer.us.mynotes";
        Random randomSeed = new Random();
        int seed = randomSeed.nextInt();
        Random random = new Random(seed);
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        ;
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextInt());
        testActions.add(createNoteButton);
        Action addNoteText = ReadUtil.generateActionFromSimpleString("TEXT, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editText1]", random.nextInt());
        testActions.add(addNoteText);
        Action saveNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextInt());
        testActions.add(saveNoteButton);
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, new ArrayList<>(), new ArrayList<>());
        testCase.executeBefore();
        List<String> initialState = labelsDetection();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setInitialState(initialState);
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.contains(testActions[1].value)");
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.write(appPackage);
        writerUtil.write(String.valueOf(seed));
        writerUtil.write(String.valueOf(testActions.size()));
        for (Action action : testActions) {
            writerUtil.write(action.toString());
        }
        writerUtil.write(testCase.getPredicate().toString());
        /*writerUtil.write(initialState.toString());
        writerUtil.write(finalState.toString());*/
        return testCase;
    }

    public static TestCase editTestCase() throws UiObjectNotFoundException {
        Log.d("ISA", "Building Test case...");
        String appPackage = "esadrcanfer.us.mynotes";
        Random randomSeed = new Random();
        int seed = randomSeed.nextInt();
        Random random = new Random(seed);
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        ;
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editButton]", random.nextInt());
        testActions.add(createNoteButton);
        Action addNoteText = ReadUtil.generateActionFromSimpleString("TEXT, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editText1]", random.nextInt());
        testActions.add(addNoteText);
        Action saveNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextInt());
        testActions.add(saveNoteButton);
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, new ArrayList<>(), new ArrayList<>());
        testCase.executeBefore();
        List<String> initialState = labelsDetection();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.contains(testActions[1].value)");
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.write(appPackage);
        writerUtil.write(String.valueOf(seed));
        writerUtil.write(String.valueOf(testActions.size()));
        for (Action action : testActions) {
            writerUtil.write(action.toString());
        }
        writerUtil.write(testCase.getPredicate().toString());
        /*writerUtil.write(initialState.toString());
        writerUtil.write(finalState.toString());*/
        return testCase;
    }

    public static TestCase deleteTestCase() throws UiObjectNotFoundException {
        Log.d("ISA", "Building Test case...");
        String appPackage = "esadrcanfer.us.mynotes";
        Random randomSeed = new Random();
        int seed = randomSeed.nextInt();
        Random random = new Random(seed);
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        ;
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editButton]", random.nextInt());
        testActions.add(createNoteButton);
        Action saveNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button2]", random.nextInt());
        testActions.add(saveNoteButton);
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, new ArrayList<>(), new ArrayList<>());
        testCase.executeBefore();
        List<String> initialState = labelsDetection();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setInitialState(initialState);
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.size() < initialState.size()");
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.write(appPackage);
        writerUtil.write(String.valueOf(seed));
        writerUtil.write(String.valueOf(testActions.size()));
        for (Action action : testActions) {
            writerUtil.write(action.toString());
        }
        writerUtil.write(testCase.getPredicate().toString());
        /*writerUtil.write(initialState.toString());
        writerUtil.write(finalState.toString());*/
        return testCase;
    }

    public static List<String> labelsDetection() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        List<String> finalState = new ArrayList<>();
        List<UiObject2> elements = device.findObjects(By.clazz(TextView.class));
        for (UiObject2 label : elements) {
            String text = label.getText();
            //Solución básica, hay que mejorarla
            if (!(text.contains(":") || text.contains("%"))) {
                finalState.add(text);
            }
        }
        return finalState;
    }
}
