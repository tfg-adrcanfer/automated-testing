package esadrcanfer.us.alumno.autotesting.util;

import android.os.Environment;
import android.os.TestLooperManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.TextInputAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.TextInputGenerator;

public class ReadUtil {
    String path;

    public ReadUtil(String path){
        this.path = path;
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
        String action = "";
        for(int i = 1; i< lines.length; i++){
            action = lines[i];
            testActions.add(generateActionFromString(action));
        }
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));

        return new TestCase(appPackage, Collections.EMPTY_SET,beforeActions,testActions,afterActions);
    }

    private Action generateActionFromString(String action){
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
                TextInputGenerator generator = new TextInputGenerator();
                res = new TextInputAction(object, generator);
        }
        return res;
    }
}
