package esadrcanfer.us.alumno.autotesting;

import java.util.List;
import java.util.Set;

import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class TestCase {
    String app;
    Set<String> exceutionContext;
    List<Action> beforeActions;
    List<Action> testActions;
    List<Action> afterActions;

    public TestCase(String app, Set<String> exceutionContext, List<Action> beforeActions, List<Action> testActions, List<Action> afterActions) {
        this.app = app;
        this.exceutionContext = exceutionContext;
        this.beforeActions = beforeActions;
        this.testActions = testActions;
        this.afterActions = afterActions;
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

    public void executeTest() throws UiObjectNotFoundException {
        for(Action a:afterActions)
            a.perform();
    }

    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder("Test Case["+testActions.size()+"]:");
        for(Action a:testActions)
            builder.append(a.toString());
        return builder.toString();
    }

}
