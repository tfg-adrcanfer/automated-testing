package esadrcanfer.us.alumno.autotesting.objectivefunctions.graph;

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
        }catch(Exception e){
            Log.d("TFG", "Se ha cerrado la aplicación");
            result=1;
        } finally {
            try {
                test.executeAfter();
            } catch (UiObjectNotFoundException e1) {

            }
        }
        return result;
    }
}
