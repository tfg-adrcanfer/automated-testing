package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class ButtonAction extends Action {

    String targetText;
    String targetClass;
    public ButtonAction(UiObject button)
    {
        super(button);
        targetText="";
        targetClass="";
    }

    public void perform() throws UiObjectNotFoundException {
        targetText=target.getText();
        targetClass=target.getClassName();
        this.target.click();
    }

    @Override
    public String toString()
    {
        return "Click Button "+targetText;
    }
}
