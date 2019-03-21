package esadrcanfer.us.alumno.autotesting;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.util.PredicateEvaluator;

public class TestCase {
    String app;
    Set<String> exceutionContext;
    List<Action> beforeActions;
    List<Action> testActions;
    List<Action> afterActions;
    List<String> finalState;
    String predicate;

    public TestCase(String app, Set<String> exceutionContext, List<Action> beforeActions, List<Action> testActions, List<Action> afterActions, List<String> finalState) {
        this.app = app;
        this.exceutionContext = exceutionContext;
        this.beforeActions = beforeActions;
        this.testActions = testActions;
        this.afterActions = afterActions;
        this.finalState = finalState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCase)) return false;

        TestCase testCase = (TestCase) o;

        if (!app.equals(testCase.app)) return false;
        if (!exceutionContext.equals(testCase.exceutionContext)) return false;
        if (!beforeActions.equals(testCase.beforeActions)) return false;
        if (!testActions.equals(testCase.testActions)) return false;
        return afterActions.equals(testCase.afterActions);
    }

    @Override
    public int hashCode() {
        int result = app.hashCode();
        result = 31 * result + exceutionContext.hashCode();
        result = 31 * result + beforeActions.hashCode();
        result = 31 * result + testActions.hashCode();
        result = 31 * result + afterActions.hashCode();
        return result;
    }

    public void executeBefore() throws UiObjectNotFoundException {
        for(Action a:beforeActions)
            a.perform();
    }

    public void executeAfter() throws UiObjectNotFoundException {
        for(Action a:afterActions)
            a.perform();
    }

    public boolean executeTest(){
        Boolean res=false;
        try{
            for(Action a:testActions) {
                a.perform();
            }
            if(finalState.size() != 0 && predicate!=null){
                Log.d("ISA", "Checking test");
                PredicateEvaluator predicateEvaluator = new PredicateEvaluator();
                res = predicateEvaluator.evaluate(this);
            }
            if(predicate==null)
                res=true;

        } catch (UiObjectNotFoundException e){

        }
        return res;
    }

    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder("Test Case["+testActions.size()+"]:");
        for(Action a:testActions)
            builder.append(a.toString());
        return builder.toString();
    }

    public List<Action> getTestActions(){
        return new ArrayList<>(testActions);
    }

    public List<String> getFinalState(){
        return new ArrayList<>(finalState);
    }

    public void setFinalState(List<String> finalState){
        this.finalState = finalState;
    }

    public String getPredicate(){
        return predicate;
    }

    public String getAppPackage(){
        return app;
    }

    public void setPredicate(String predicate){
        this.predicate = predicate;
    }

    public double compareTestCase (TestCase testCase){
        double res = 0;
        for(Action a: this.testActions){
            for(Action a2: testCase.testActions){
                if(a.toString().compareTo(a2.toString()) != 0){
                    res++;
                }
            }
        }
        return res;
    }

}
