package esadrcanfer.us.alumno.autotesting.algorithms;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

public class GRASPReparation extends BaseReparationAlgorithm {

    int maxIterations;
    int maxCreationIterations;
    int maxImprovementIterations;
    List<Action> beforeActions;
    List<Action> afterActions;
    List<Action> testActions;
    List<Action> previousValidTestActions;
    List<Action> actionsAfterBreakingPoint;
    TestCase bugTestCase;
    int breakingPoint;
    Random random;
    TestCase optimum;
    Double currentOptimum;

    ObjectiveFunction objectiveFunction;

    public GRASPReparation(int maxIterations) {
        this(maxIterations,10,10);
    }
    public GRASPReparation(int maxIterations, int maxCreationIterations, int maxImprovementIterations) {
        this.maxIterations = maxIterations;
        this.maxCreationIterations=maxCreationIterations;
        this.maxImprovementIterations=maxImprovementIterations;
    }


    @Override
    public TestCase repair(UiDevice device, TestCase buggyTestCase, int breakingPoint) throws UiObjectNotFoundException {
        String appPackage=buggyTestCase.getAppPackage();
        beforeActions = new ArrayList<>();
        beforeActions.add(new StartAppAction(appPackage));
        afterActions = new ArrayList<>();
        afterActions.add(new CloseAppAction(appPackage));
        testActions = new ArrayList<>();

        previousValidTestActions=new ArrayList<>();
        for(int i=0;i<breakingPoint;i++)
            previousValidTestActions.add(buggyTestCase.getTestActions().get(i));

        actionsAfterBreakingPoint=new ArrayList<>();
        for(int i=breakingPoint+1;i<buggyTestCase.getTestActions().size();i++)
            actionsAfterBreakingPoint.add(buggyTestCase.getTestActions().get(i));

        this.breakingPoint=breakingPoint;
        return run(device,appPackage);
    }

    public TestCase run(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        List<Action> testCaseActions;
        WriterUtil writerUtil = null;
        Action chosenAction;
        TestCase res = null;
        Double currentEvaluation;
        for (int iterations=0;iterations< maxIterations;iterations++) {
            Log.d("ISA", "Running iteration " + (iterations + 1));
            res = creationPhase(device, appPackage);
            currentEvaluation=objectiveFunction.evaluate(res,res.getAppPackage());
            Log.d("ISA", "Evaluation at creation: " + currentEvaluation);
            if (currentEvaluation!=null && (currentOptimum==null || currentEvaluation>=currentOptimum)){
                optimum=res;
                currentOptimum=currentEvaluation;
            }
            if(optimum.getPredicate().getNClauses()==currentOptimum)
                break;
            res = improvementPhase(device,res);
            currentEvaluation = objectiveFunction.evaluate(res,res.getAppPackage());
            Log.d("ISA", "Eval at improvement: " + currentEvaluation);
            if (currentEvaluation!=null && (currentOptimum==null || currentEvaluation>=currentOptimum)){
                    optimum=res;
                    currentOptimum=currentEvaluation;
            }
            if(optimum.getPredicate().getNClauses()==currentOptimum)
                break;
        }
        //TODO Escribir el testCase en un fichero
        /*    writerUtil = new WriterUtil();
            writerUtil.write(appPackage);
            writerUtil.write(selectedSeed.toString());
            for (Action a : testActions) {
                writerUtil.write(a.toString());
            }
        */

        return res;
    }

    public TestCase creationPhase(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        // Initialization
        TestCase result=null;
        List<Action> testCaseActions=new ArrayList<>();
        List<Action> availableActions;
        List<Action> RCL;
        // Main Loop:
        startApp(appPackage);
        int creationIterations=0;
        while(testActions.size()<bugTestCase.getTestActions().size() && creationIterations<maxCreationIterations) {
            availableActions = identifyAvailableActions(device);
            RCL = chooseRestrictedCandidatesList(device,availableActions);
            testCaseActions.add(chooseRandomAction(RCL));
            creationIterations++;
        }
        List<String> finalState = labelsDetection();
        closeApp(appPackage);
        /*
        while (testCaseActions.size() < bugTestCase.getTestActions().size() && availableActions.size() > 0) {
            chosenAction = availableActions.get(getRandom().nextInt(availableActions.size()));
            testCaseActions.add(chosenAction);
            Log.d("ISA", "Executing action: " + chosenAction);
            chosenAction.perform();
            String appName = UiDevice.getInstance().getCurrentPackageName();
            if (!appName.equals(appPackage)) {
                break;
            }
            availableActions = createAction(device, seeds.nextInt());
        }*/
        // Reporting and results creation:
        result = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testCaseActions, afterActions, finalState);
        result.setPredicate(bugTestCase.getPredicate());
        return result;
    }

    public List<Action> identifyAvailableActions(UiDevice device){
        return createAction(device, random.nextInt());
    }

    public List<Action> chooseRestrictedCandidatesList(UiDevice device,List<Action> availableActions) {
        List<Action> RCL=new ArrayList<>();

        for(Action candidate:availableActions){
            if(actionsAfterBreakingPoint.contains(candidate) && !testActions.contains(candidate))
                RCL.add(candidate);
        }
        if(RCL.isEmpty())
            RCL.addAll(availableActions);
        return RCL;
    }



    public TestCase improvementPhase(UiDevice device,TestCase inputTestCase) throws UiObjectNotFoundException {
        TestCase result=inputTestCase;
        int index;
        for(int i=0;i<maxImprovementIterations;i++){
            index=random.nextInt(result.getTestActions().size());
            if(random.nextDouble()<0.5) {
               result=additionImprovement(device,index,result);
            }else
                result=removalImprovement(device,index,result);
        }
        return result;
    }

    public TestCase additionImprovement(UiDevice device, int index,TestCase inputTestCase) throws UiObjectNotFoundException {
        TestCase result=inputTestCase;
        Double optimum=objectiveFunction.evaluate(result,result.getAppPackage());
        Double candidateEvaluation;
        executeUntil(device,index,inputTestCase);
        List<Action> availableActions=identifyAvailableActions(device);
        List<TestCase> feasibleAdditions=findFeasibleAdditons(device,index, inputTestCase,availableActions);
        for(TestCase addition:feasibleAdditions){
            candidateEvaluation=objectiveFunction.evaluate(addition,addition.getAppPackage());
            if(candidateEvaluation!=null && candidateEvaluation>=optimum) {
                result=addition;
                optimum=candidateEvaluation;
            }
        }
        return result;

    }

    public void executeUntil(UiDevice device, int index,TestCase inputTestCase) throws UiObjectNotFoundException {
        inputTestCase.executeBefore();
        for(int i=0;i<index;i++){
            inputTestCase.getTestActions().get(i).perform();
        }
    }

    public List<TestCase> findFeasibleAdditons(UiDevice device,int index,TestCase inputTestCase, List<Action> availableActions){
        List<TestCase> result=new ArrayList<>();
        TestCase testCase;
        for(Action action:availableActions){
            testCase=new TestCase(inputTestCase);
            testCase.getTestActions().add(index,action);
            result.add(testCase);
        }
        return result;
    }

    public TestCase removalImprovement(UiDevice device,int index,TestCase inputTestCase) throws UiObjectNotFoundException {
        TestCase newTestCase=new TestCase(inputTestCase);
        newTestCase.getTestActions().remove(index);
        Double evaluation=objectiveFunction.evaluate(newTestCase,newTestCase.getAppPackage());
        if(evaluation!=null){
            return newTestCase;
        }else
            return inputTestCase;

    }



    public Action chooseRandomAction(List<Action> actions){
        return actions.get(random.nextInt(actions.size()));
    }


}
