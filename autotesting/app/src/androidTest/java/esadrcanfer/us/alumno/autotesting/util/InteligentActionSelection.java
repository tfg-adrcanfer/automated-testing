package esadrcanfer.us.alumno.autotesting.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class InteligentActionSelection {
	
	private static Map<Action, Integer> invertedValues(Map<Action, Integer> originalValues){
		Map<Action, Integer> first = new HashMap<>();
		Map<Action, Double> second = new HashMap<>();
		Map<Action, Integer> res = new HashMap<>();
		
		for(Map.Entry<Action, Integer> entry: originalValues.entrySet()) {
			first.put(entry.getKey(), (entry.getValue()+1));
		}
		
		for(Map.Entry<Action, Integer> entry: first.entrySet()) {
			second.put(entry.getKey(), (double) 1/(entry.getValue()));
		}
		
		Integer lcm = (int) lcm(new ArrayList<Integer>(first.values()));
		
		for(Map.Entry<Action, Double> entry: second.entrySet()) {
			res.put(entry.getKey(), new Integer((int) (entry.getValue()*lcm)));
		}
		return res;
	}
	
	private static Integer elementsSum(Map<Action, Integer> originalValues) {
		Integer res = 0;
		for (Integer value : originalValues.values()) {
			res = res + value + 1;
		}
		return res;
	}
	
	private static long gcd(long a, long b)
	{
	    while (b > 0)
	    {
	        long temp = b;
	        b = a % b; // % is remainder
	        a = temp;
	    }
	    return a;
	}

	
	private static long lcm(long a, long b)
	{
	    return a * (b / gcd(a, b));
	}

	private static long lcm(List<Integer> input)
	{
	    long result = input.get(0);
	    for(int i = 1; i < input.size(); i++) result = lcm(result, input.get(i));
	    return result;
	}
	
	public static Action selectAction(Map<Action, Integer> elements) {
		Random random = new Random();
		Map<Action, Integer> inverseElements = InteligentActionSelection.invertedValues(elements);
		Integer elementsCount = elementsSum(elements);
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

	public static Map<Action, Integer> countActions(List<TestCase> testCases, List<Action> testCaseActions, List<Action> availableActions){
        Map<Action, Integer> res = new HashMap<>();
        for (TestCase t: testCases) {
            for(Action a: t.getTestActions()){
                if(availableActions.contains(a)){
                    if(new ArrayList<Action>(res.keySet()).contains(a)){
                        res.put(a, res.get(a)+1);
                    } else {
                        res.put(a, 1);
                    }
                }
            }
        }
        for(Action a: testCaseActions){
            if(availableActions.contains(a)){
                if(new ArrayList<Action>(res.keySet()).contains(a)){
                    res.put(a, res.get(a)+1);
                } else {
                    res.put(a, 1);
                }
            }
        }
        for(Action a: availableActions){
            if(!new ArrayList<Action>(res.keySet()).contains(a)){
                res.put(a, 0);
            }
        }
        return res;
	}

}
