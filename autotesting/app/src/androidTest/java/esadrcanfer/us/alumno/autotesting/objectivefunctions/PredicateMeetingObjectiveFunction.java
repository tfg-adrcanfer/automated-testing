package esadrcanfer.us.alumno.autotesting.objectivefunctions;

import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;

public class PredicateMeetingObjectiveFunction implements ObjectiveFunction {

    @Override
    public Double evaluate(TestCase testcase, String appPackage) {
        Double result=null;
        try {
            testcase.executeBefore();
            testcase.executeTest();
            result = new Double(testcase.getPredicate().nClausesMeet(testcase));
            testcase.executeAfter();
        }catch(UiObjectNotFoundException ex){
            return null;
        }
        return result;
    }
}
