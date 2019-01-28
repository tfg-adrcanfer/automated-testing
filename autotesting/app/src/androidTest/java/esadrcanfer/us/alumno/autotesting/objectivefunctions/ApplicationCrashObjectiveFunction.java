package esadrcanfer.us.alumno.autotesting.objectivefunctions;

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
            result=1;
        }
        return result;
    }
}
