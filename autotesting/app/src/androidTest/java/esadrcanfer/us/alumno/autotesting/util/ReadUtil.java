package esadrcanfer.us.alumno.autotesting.util;

import android.os.Environment;
import android.os.TestLooperManager;
import android.util.Log;
import android.widget.Toast;

import junit.framework.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.CheckBoxAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonInputGenerator;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.TextInputAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.TextInputGenerator;

public class ReadUtil {
    String path;
    Boolean sameSeed;

    public ReadUtil(String path, Boolean sameSeed){
        this.path = path;
        this.sameSeed = sameSeed;
    }

    public String getPath(){
        return this.path;
    }

    public String readText(){
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                    Environment.getExternalStorageDirectory().getAbsolutePath().toString()
                            + "/" + getPath()));
            String line;
            while ((line = br.readLine())!= null){
                text.append(line);
                text.append("\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public TestCase generateTestCase(){
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        String text = readText();
        String[] lines = text.split("\n");
        String appPackage = lines[0];
        Long seed;
        if(sameSeed){
            seed = new Long(lines[1]);
        } else {
            seed = new Random().nextLong();
        }
        Integer actionsSize = new Integer(lines[2]);
        Random random = new Random(seed);
        String action = "";
        for(int i = 3; i<= actionsSize + 2; i++){
            action = lines[i];
            testActions.add(generateActionFromString(action, random.nextInt()));
            if(i == actionsSize+2){
                actionsSize = i;
                break;
            }
        }
        String predicate = lines[actionsSize+1];
        String finalState = lines[actionsSize+2].replaceAll("\\[", "").replaceAll("\\]", "");
        List<String> labels = new ArrayList<>();
        for (String label: finalState.split(", ")) {
            labels.add(label);
        }
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET,beforeActions,testActions,afterActions, labels);
        testCase.setPredicate(predicate);
        return testCase;
    }

    public Action generateActionFromString(String action, Integer seed){
        String[] splitAction = action.split(",");
        String type = splitAction[0];
        String resourceId = splitAction[1];
        String value = splitAction[2];
        resourceId = splitAction[1].substring(resourceId.indexOf("=") + 1 ,resourceId.length()-1);
        Action res = null;
        UiObject object = new UiObject(new UiSelector().resourceId(resourceId));
        switch (type) {
            case "BUTTON":
                res = new ButtonAction(object);
                break;
            case "TEXT":
                TextInputGenerator textInputGenerator = new TextInputGenerator(seed);
                res = new TextInputAction(object, textInputGenerator);
                break;
            case "CHECKBOX":
                res = new CheckBoxAction(object);
                break;
            case "RADIO_BUTTON":
                RadioButtonInputGenerator radioButtonInputGenerator = new RadioButtonInputGenerator(seed);
                res = new RadioButtonAction(object, radioButtonInputGenerator);
        }
        Log.d("ISA", "Action: " + action);
        Log.d("ISA", "Value: " + value);
        res.setValue(value);
        return res;
    }

    public static Action generateActionFromSimpleString(String action, Integer seed){
        Log.d("ISA", action);
        String[] splitAction = action.split(",");
        String type = splitAction[0];
        String resourceId = splitAction[1];
        resourceId = splitAction[1].substring(resourceId.indexOf("=") + 1 ,resourceId.length()-1);
        Action res = null;
        UiObject object = new UiObject(new UiSelector().resourceId(resourceId));
        switch (type){
            case "BUTTON":
                res = new ButtonAction(object);
                break;
            case "TEXT":
                TextInputGenerator textInputGenerator = new TextInputGenerator(seed);
                res = new TextInputAction(object, textInputGenerator);
                break;
            case "CHECKBOX":
                res = new CheckBoxAction(object);
                break;
            case "RADIO_BUTTON":
                RadioButtonInputGenerator radioButtonInputGenerator = new RadioButtonInputGenerator(seed);
                res = new RadioButtonAction(object, radioButtonInputGenerator);
        }
        return res;
    }
}
