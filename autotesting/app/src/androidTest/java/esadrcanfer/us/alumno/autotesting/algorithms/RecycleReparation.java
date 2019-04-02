package esadrcanfer.us.alumno.autotesting.algorithms;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
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
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

import static android.os.SystemClock.sleep;
import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

public class RecycleReparation {

    long maxIterations;
    List<Action> beforeActions;
    List<Action> afterActions;
    List<Action> testActions;
    TestCase bugTestCase;
    Random random;
    int breakingPoint;

    public RecycleReparation(long maxIterations, TestCase bugTestCase, int breakingPoint) {
        this.maxIterations = maxIterations;
        this.bugTestCase = bugTestCase;
        beforeActions = new ArrayList<>();
        beforeActions.add(new StartAppAction(bugTestCase.getAppPackage()));
        afterActions = new ArrayList<>();
        afterActions.add(new CloseAppAction(bugTestCase.getAppPackage()));
        testActions = new ArrayList<>();
        this.breakingPoint = breakingPoint;
    }

    public TestCase run(UiDevice device) throws UiObjectNotFoundException {

        List<Action> testCaseActions;
        List<Action> availableActions;
        WriterUtil writerUtil = null;
        Action chosenAction;
        TestCase res = null;
        Boolean eval = false;
        List<Action> validActions = new ArrayList<>();
        for(int i=0;i<breakingPoint;i++)
            validActions.add(bugTestCase.getTestActions().get(i));
        int i = 0;

        while (i < maxIterations) {
            Log.d("ISA", "Running iteration " + (i + 1));
            Random chosenSeed = new Random();
            Long seed = chosenSeed.nextLong();
            Random seeds = new Random(seed);
            startApp(bugTestCase.getAppPackage());
            testCaseActions = new ArrayList<>(validActions);
            for (Action a: testCaseActions){
                a.perform();
            }
            availableActions = createAction(device, seeds.nextInt());
            List<String> initialState = labelsDetection();
            while (testCaseActions.size() < bugTestCase.getTestActions().size() && availableActions.size() > 0) {
                chosenAction = availableActions.get(getRandom().nextInt(availableActions.size()));
                testCaseActions.add(chosenAction);
                Log.d("ISA", "Executing action: " + chosenAction);
                chosenAction.perform();
                String appName = UiDevice.getInstance().getCurrentPackageName();
                if (!appName.equals(bugTestCase.getAppPackage())) {
                    break;
                }
                availableActions = createAction(device, seeds.nextInt());
            }
            List<String> finalState = labelsDetection();
            closeApp(bugTestCase.getAppPackage());
            res = new TestCase(bugTestCase.getAppPackage(), Collections.EMPTY_SET, beforeActions, testCaseActions, afterActions, initialState, finalState);
            res.setPredicate(bugTestCase.getPredicate());
            eval = res.evaluate();
            sleep(2000);
            Log.d("ISA", "Eval: " + eval);
            i++;
            if (eval == true) {
                break;
            }
        }
        //TODO Escribir el testCase en un fichero
        /*    writerUtil = new WriterUtil();
            writerUtil.write(appPackage);
            writerUtil.write(selectedSeed.toString());
            for (Action a : testActions) {
                writerUtil.write(a.toString());
            }
        */

        return res;
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

    public Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    /**public boolean isSameNode(UiDevice device, List<Action> availableActions, Random random) {
     boolean result = true;
     List<Action> actions = new ArrayList<>(ActionFactory.createActions(device, random).values());
     for (int i = 0; i < actions.size() && result; i++)
     result = (result && availableActions.contains(actions.get(i)));
     result = (result && availableActions.size() == (actions.size()));
     return result;
     }**/
}
