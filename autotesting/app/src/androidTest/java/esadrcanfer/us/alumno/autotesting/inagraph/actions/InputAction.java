package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;


public class InputAction extends Action {
    InputGenerator inputGenerator;
    public InputAction(UiObject target, InputGenerator generator){
        super(target);
        this.inputGenerator=generator;
    }

    public void perform() throws UiObjectNotFoundException
    {
        inputGenerator.generateInput(target);
    }

    @Override
    public String toString() {
        return "generate input";
    }
}
