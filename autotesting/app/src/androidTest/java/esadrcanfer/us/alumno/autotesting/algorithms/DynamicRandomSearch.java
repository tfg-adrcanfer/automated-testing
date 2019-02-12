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
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

public class DynamicRandomSearch {
    long iterations;
    long actionsLength;
    List<Action> beforeActions;
    List<Action> afterActions;
    List<Action> testActions;
    Boolean saveAllTestCases;

    public DynamicRandomSearch(long iterations, int actionsLength, String appPackage, Boolean saveAllTestCases) {
        this.iterations = iterations;
        this.actionsLength=actionsLength;
        this.saveAllTestCases = saveAllTestCases;
        beforeActions=new ArrayList<>();
        beforeActions.add(new StartAppAction(appPackage));
        afterActions=new ArrayList<>();
        afterActions.add(new CloseAppAction(appPackage));
        testActions=new ArrayList<>();
    }

    public TestCase run(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        int i=0;
        List<Action> testCaseActions;
        List<Action> availableActions;
        WriterUtil writerUtil = null;
        Action chosenAction;
        double currentBestEval = -100;
        double eval = -100;
        while(i<iterations){
            Log.d("TFG","Running iteration "+(i+1));
            if(saveAllTestCases){
                writerUtil = new WriterUtil();
                writerUtil.write(appPackage);
            }
            startApp(appPackage);
            testCaseActions = new ArrayList<>();
            availableActions = createAction(device);
            while(testCaseActions.size()<actionsLength && availableActions.size() > 0){
                if(!isSameNode(device, availableActions)){
                    availableActions = createAction(device);
                }
                chosenAction=availableActions.get((int)(Math.random()*availableActions.size()));
                testCaseActions.add(chosenAction);
                Log.d("TFG","Executing action: "+ chosenAction);
                if(saveAllTestCases){
                    writerUtil.write(chosenAction.toString());
                }
                eval = evaluate(chosenAction, appPackage);
            }
            if(eval>currentBestEval){
                currentBestEval=eval;
                testActions = new ArrayList<>();
                testActions.addAll(testCaseActions);
            }
            closeApp(appPackage);
            i++;
        }


        if(!saveAllTestCases){
            writerUtil = new WriterUtil();
            writerUtil.write(appPackage);
            for(Action a: testActions){
                writerUtil.write(a.toString());
            }
        }
        return new TestCase(appPackage, Collections.EMPTY_SET,beforeActions,testActions,afterActions);
    }

    private List<Action> createAction(UiDevice device) {
        Map<UiObject, Action> actions;
        actions = ActionFactory.createActions(device);
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

    public boolean isSameNode(UiDevice device, List<Action> availableActions) {
        boolean result = true;
        List<Action> actions = new ArrayList<>(ActionFactory.createActions(device).values());
        for (int i = 0; i < actions.size() && result; i++)
            result = (result && availableActions.contains(actions.get(i)));
        result = (result && availableActions.size() == (actions.size()));
        return result;
    }

    public double evaluate(Action action, String appPackage) {
        double result=0;
        try {
            UiDevice device = UiDevice.getInstance();
            String packageName = device.getCurrentPackageName();
            action.perform();
            if(!packageName.equals(device.getCurrentPackageName())){
                result = 1;
            }
        }catch(Exception e){
        }
        return result;
    }
}
