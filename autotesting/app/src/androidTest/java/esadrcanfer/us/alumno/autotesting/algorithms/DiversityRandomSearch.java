package esadrcanfer.us.alumno.autotesting.algorithms;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ActionFactory;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

import static android.os.SystemClock.sleep;

public class DiversityRandomSearch {
    long iterations;
    long actionsLength;
    long diversityLength;
    List<Action> beforeActions;
    List<Action> afterActions;
    List<TestCase> testCases;
    Boolean saveAllTestCases;
    double diferentActions;

    public DiversityRandomSearch(long iterations, long diversityLength, int actionsLength, String appPackage, Boolean saveAllTestCases) {
        this.iterations = iterations;
        this.actionsLength = actionsLength;
        this.diversityLength = diversityLength;
        this.saveAllTestCases = saveAllTestCases;
        this.diferentActions = 0;
        beforeActions = new ArrayList<>();
        beforeActions.add(new StartAppAction(appPackage));
        afterActions = new ArrayList<>();
        afterActions.add(new CloseAppAction(appPackage));
        testCases = new ArrayList<>();
    }

    public List<TestCase> run(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        int i = 0;
        TestCase testCase;
        while (i < iterations) {
            Log.d("TFG", "Running iteration " + (i + 1));
            testCase = buildTestCase(device, appPackage);
            if (i < diversityLength) {
                testCases.add(testCase);
            } else {
                testCases = new ArrayList<>(diversityMeasure(testCase));
                diferentActions = evaluateDiversityTestCases(testCases);
            }
            if (i == diversityLength - 1) {
                diferentActions = evaluateDiversityTestCases(testCases);
            }
            Log.d("TFG", "Diferent actions iteration " + (i+1) +": " + diferentActions);
            i++;
        }
        if(!saveAllTestCases){
            WriterUtil writerUtil;
            for (TestCase t: testCases){
                writerUtil = new WriterUtil();
                writerUtil.write(appPackage);
                writerUtil.write("-10000");
                for(Action a: t.getTestActions()){
                    writerUtil.write(a.toString());
                }
                sleep(1000);
            }
        }

        return testCases;
    }

    private TestCase buildTestCase(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        List<Action> testCaseActions;
        List<Action> availableActions;
        Random chosenSeed = new Random();
        Long seed = chosenSeed.nextLong();
        Random seeds = new Random(seed);
        Action chosenAction;
        startApp(appPackage);
        testCaseActions = new ArrayList<>();
        availableActions = createAction(device, seeds.nextInt());
        WriterUtil writerUtil = null;
        if(saveAllTestCases){
            writerUtil = new WriterUtil();
            writerUtil.write(appPackage);
            writerUtil.write(seed.toString());
        }
        while (testCaseActions.size() < actionsLength && availableActions.size() > 0) {
            chosenAction = availableActions.get((int) (Math.random() * availableActions.size()));
            testCaseActions.add(chosenAction);
            Log.d("TFG", "Executing action: " + chosenAction);
            chosenAction.perform();
            if(saveAllTestCases){
                writerUtil.write(chosenAction.toString());
            }
            availableActions = createAction(device, seeds.nextInt());
        }
        closeApp(appPackage);
        return new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testCaseActions, afterActions);
    }

    private List<Action> createAction(UiDevice device, Integer seed) {
        Map<UiObject, Action> actions;
        actions = ActionFactory.createActions(device, seed);
        return new ArrayList<>(actions.values());
    }

    private void closeApp(String appPackage) {
        CloseAppAction action = new CloseAppAction(appPackage);
        action.perform();
    }

    private void startApp(String appPackage) throws UiObjectNotFoundException {
        StartAppAction action = new StartAppAction(appPackage);
        action.perform();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }

    public boolean isSameNode(UiDevice device, List<Action> availableActions, Random random) {
        boolean result = true;
        List<Action> actions = new ArrayList<>(ActionFactory.createActions(device, random.nextInt()).values());
        for (int i = 0; i < actions.size() && result; i++)
            result = (result && availableActions.contains(actions.get(i)));
        result = (result && availableActions.size() == (actions.size()));
        return result;
    }

    private List<TestCase> diversityMeasure(TestCase chosenTestCase) {
        double currentEval = 0;
        double bestEval = diferentActions;
        List<TestCase> currentTestCases;
        List<TestCase> bestTestCases = new ArrayList<>(testCases);
        for (int i = 0; i < testCases.size(); i++) {
            currentTestCases = new ArrayList<>(testCases);
            currentTestCases.set(i, chosenTestCase);
            currentEval = evaluateDiversityTestCases(currentTestCases);
            if(currentEval > bestEval){
                bestEval = currentEval;
                bestTestCases = new ArrayList<>(currentTestCases);
            }
        }
        return bestTestCases;
    }

    private double evaluateDiversityTestCases(List<TestCase> testCases) {
        List<String> actions = new ArrayList<>();
        for (TestCase selectedTestCase : testCases) {
            for (Action action : selectedTestCase.getTestActions()) {
                if (!actions.contains(action.toString())) {
                    actions.add(action.toString());
                }
            }
        }
        return actions.size() * 1.0;
    }
}
