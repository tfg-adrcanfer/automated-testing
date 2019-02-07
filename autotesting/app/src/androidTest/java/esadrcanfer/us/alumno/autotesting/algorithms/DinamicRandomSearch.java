package esadrcanfer.us.alumno.autotesting.algorithms;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ActionFactory;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;

public class DinamicRandomSearch {

    ObjectiveFunction objective;
    long iterations;
    long actionsLength;

    public DinamicRandomSearch(ObjectiveFunction objective, long iterations, int actionsLength) {
        this.objective = objective;
        this.iterations = iterations;
        this.actionsLength=actionsLength;
    }

    public TestCase run(UiDevice device, String app) throws UiObjectNotFoundException {

        Log.d("TFG","Running iteration 1");
        TestCase candidate=buildRandomTestCase(device, app);
        Log.d("TFG", "Choosen actions: " + candidate);
        TestCase result=candidate;
        double eval=objective.evaluate(result);
        double currentBestEval=eval;
        int i=1;
        while(i<iterations){
            Log.d("TFG","Running iteration "+(i+1));
            candidate=buildRandomTestCase(device, app);
            Log.d("TFG", "Choosen actions: " + candidate);
            eval=objective.evaluate(candidate);
            if(eval>currentBestEval){
                currentBestEval=eval;
                result=candidate;
            }
            i++;
        }
        return result;
    }

    private TestCase buildRandomTestCase(UiDevice device, String app) throws UiObjectNotFoundException {
        List<Action> beforeActions=new ArrayList<>();
        beforeActions.add(new StartAppAction(app));
        List<Action> afterActions=new ArrayList<>();
        afterActions.add(new CloseAppAction(app));
        List<Action> testActions=new ArrayList<>();
        List<Action> candidateActions=null;
        Action chosenAction=null;
        List<Action> availableActions = createAction(device, app);
        while(testActions.size()<actionsLength && availableActions.size() > 0){
            candidateActions=availableActions;
            chosenAction=candidateActions.get((int)(Math.random()*candidateActions.size()));
            testActions.add(chosenAction);
        }
        return new TestCase(app, Collections.EMPTY_SET,beforeActions,testActions,afterActions);
    }

    private List<Action> createAction(UiDevice device, String app) throws UiObjectNotFoundException {
        Map<UiObject, Action> actions = new HashMap<>();
        startApp(app);
        actions.putAll(ActionFactory.createButtonActions(device));
        actions.putAll(ActionFactory.createInputActions(device));
        actions.putAll(ActionFactory.createCheckBoxActions(device));
        actions.putAll(ActionFactory.createRadioActions(device));
        closeApp(app);
        return new ArrayList<>(actions.values());
    }

    private void closeApp(String appPackage) throws UiObjectNotFoundException {
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
}
