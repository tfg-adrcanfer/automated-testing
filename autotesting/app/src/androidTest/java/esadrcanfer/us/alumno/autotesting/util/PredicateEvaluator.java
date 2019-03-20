package esadrcanfer.us.alumno.autotesting.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;

import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class PredicateEvaluator {
    private ExpressionParser expressionParser=new SpelExpressionParser();
    public boolean evaluate(String predicate, List<Action> actions) throws IllegalArgumentException {
        boolean result = false;
        Expression exp = expressionParser.parseExpression(predicate);
        EvaluationContext context = new StandardEvaluationContext();
        for (Action a: actions) {
            context.setVariable(a.toString(), a.getValue());
        }
        EvaluationContext experiment = null;
        Object assertionResult = exp.getValue(experiment);
        if (assertionResult instanceof Boolean) {
            result = (Boolean) assertionResult;
        } else {
            throw new IllegalArgumentException("The assertion '" + predicate + "' is not a valid, since its evaluation does not return a boolean result");

        }
        return result;
    }
}
