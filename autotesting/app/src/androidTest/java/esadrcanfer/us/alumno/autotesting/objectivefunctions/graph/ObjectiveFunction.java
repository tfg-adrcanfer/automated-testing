package esadrcanfer.us.alumno.autotesting.objectivefunctions.graph;

import esadrcanfer.us.alumno.autotesting.TestCase;

public interface ObjectiveFunction {
    public double evaluate(TestCase testcase, String appPackage);
}
