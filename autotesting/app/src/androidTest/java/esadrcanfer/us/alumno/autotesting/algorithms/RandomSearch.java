package esadrcanfer.us.alumno.autotesting.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.INAGraph;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;

public class RandomSearch {

    ObjectiveFunction objective;
    long iterations;
    long actionsLength;

    public RandomSearch(ObjectiveFunction objective, long iterations, int actionsLength) {
        this.objective = objective;
        this.iterations = iterations;
        this.actionsLength=actionsLength;
    }

    public TestCase run(INAGraph graph, String app) throws UiObjectNotFoundException {
        TestCase candidate=buildRandomTestCase(graph,app);
        TestCase result=candidate;
        double eval=objective.evaluate(result);
        double currentBestEval=eval;
        int i=1;
        while(i<iterations){
            System.out.println("Running iteration "+i);
            candidate=buildRandomTestCase(graph,app);
            eval=objective.evaluate(candidate);
            if(eval>currentBestEval){
                currentBestEval=eval;
                result=candidate;
            }
            i++;
        }
        return result;
    }

    private TestCase buildRandomTestCase(INAGraph graph,String app) throws UiObjectNotFoundException {
        graph.reset();
        List<Action> beforeActions=new ArrayList<>();
        beforeActions.add(new StartAppAction(app));
        List<Action> afterActions=new ArrayList<>();
        afterActions.add(new CloseAppAction(app));
        List<Action> testActions=new ArrayList<>();
        List<Action> candidateActions=null;
        Action chosenAction=null;
        while(testActions.size()<actionsLength && graph.getAvailableActions().size() > 0){
            candidateActions=graph.getAvailableActions();
            chosenAction=candidateActions.get((int)(Math.random()*candidateActions.size()));
            testActions.add(chosenAction);
            graph.performAction(chosenAction);
        }
        return new TestCase(app, Collections.EMPTY_SET,beforeActions,testActions,afterActions);
    }
}
