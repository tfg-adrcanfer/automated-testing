package esadrcanfer.us.alumno.autotesting.objectivefunctions.graph;

import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;

import static android.os.SystemClock.sleep;

public class ApplicationCrashObjectiveFunction implements ObjectiveFunction {
    @Override
    public double evaluate(TestCase test, String appPackage) {
        double result=0;
        UiDevice device = UiDevice.getInstance();
        try {
            test.executeBefore();
            test.executeTest();
        }catch(Exception e){
            Log.d("TFG", "Se ha cerrado la aplicación");
            result=1;
        } finally {
            try {

                if(!appPackage.equals(device.getCurrentPackageName())){
                    result = 1;
                }
                test.executeAfter();
            } catch (UiObjectNotFoundException e1) {

            }
        }
        return result;
    }
}
