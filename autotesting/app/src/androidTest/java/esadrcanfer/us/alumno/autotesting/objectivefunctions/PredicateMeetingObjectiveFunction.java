package esadrcanfer.us.alumno.autotesting.objectivefunctions;

import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;

public class PredicateMeetingObjectiveFunction implements ObjectiveFunction {

    @Override
    public Double evaluate(TestCase testcase, String appPackage) {
        Double result=null;
        try {
            testcase.executeBefore();
            testcase.executeTest();
            result = new Double(testcase.getPredicate().nClausesMeet(testcase));
        }catch(BrokenTestCaseException ex){
        } catch (UiObjectNotFoundException e) {
        }finally {
            try {
                testcase.executeAfter();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
