package esadrcanfer.us.alumno.autotesting.objectivefunctions;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class DynamicTestExecutionTimeObjectiveFunction implements DynamicObjectiveFunction {

    @Override
    public double evaluate(Action action, String appPackage) {
        long duration=-1;
        try{
            long start=System.currentTimeMillis();
            action.perform();
            duration=System.currentTimeMillis()-start;
        }catch(Exception e){

        }
        return (double)duration;
    }
}
