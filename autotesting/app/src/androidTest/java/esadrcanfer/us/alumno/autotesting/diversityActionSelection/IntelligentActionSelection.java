package esadrcanfer.us.alumno.autotesting.diversityActionSelection;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class IntelligentActionSelection implements ActionSelection {

    @Override
    public Action selectAction(List<TestCase> testCases, List<Action> testCaseActions, List<Action> availableActions) {
        return IntelligentActionSelection.selectAction(IntelligentActionSelection.countActions(testCases, testCaseActions, availableActions));
    }

    private static Map<Action, Integer> invertedValues(Map<Action, Integer> elements){
        Map<Action, Integer> res = new HashMap<>();
        Integer sum = elementsSum(elements, false);
        for(Map.Entry<Action, Integer> entry: elements.entrySet()) {
            res.put(entry.getKey(), sum - (entry.getValue() + 1));
        }
        return res;
    }

    private static Integer elementsSum(Map<Action, Integer> values, boolean inverted) {
        Integer res = 0;
        for (Integer value : values.values()) {
            if(!inverted){
                res = res + value + 1;
            } else {
                res = res + value;
            }

        }
        return res;
    }

    private static Action selectAction(Map<Action, Integer> elements) {
        Random random = new Random();
        Map<Action, Integer> inverseElements = invertedValues(elements);
        Integer elementsCount = elementsSum(inverseElements,  true);
        Action element = null;
        Integer randomValue = random.nextInt(elementsCount);
        for (Map.Entry<Action, Integer> entry: inverseElements.entrySet()) {
            randomValue -= entry.getValue();
            if(randomValue < 0) {
                element = entry.getKey();
                break;
            }
        }
        return element;
    }

    private static Map<Action, Integer> countActions(List<TestCase> testCases, List<Action> testCaseActions, List<Action> availableActions){
        Map<Action, Integer> res = new HashMap<>();
        for (TestCase t: testCases) {
            for(Action a: t.getTestActions()){
                if(availableActions.contains(a)){
                    if(res.keySet().contains(a)){
                        res.put(a, res.get(a)+1);
                    } else {
                        res.put(a, 1);
                    }
                }
            }
        }
        for(Action a: testCaseActions){
            if(availableActions.contains(a)){
                if(res.keySet().contains(a)){
                    res.put(a, res.get(a)+1);
                } else {
                    res.put(a, 1);
                }
            }
        }
        for(Action a: availableActions){
            if(!res.keySet().contains(a)){
                res.put(a, 0);
            }
        }
        return res;
    }
}
