package esadrcanfer.us.alumno.autotesting.objectivefunctions;

import android.util.Log;

import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;

public class ApplicationCrashObjectiveFunction implements ObjectiveFunction {
    @Override
    public double evaluate(TestCase test) {
        double result=0;
        try {
            test.executeBefore();
            test.executeTest();
            test.executeAfter();
        }catch(Exception e){
            try {
                test.executeAfter();
            } catch (UiObjectNotFoundException e1) {
                Log.d("TFG", "Se ha cerrado la aplicación");
            }
            result=1;
        }
        return result;
    }
}
