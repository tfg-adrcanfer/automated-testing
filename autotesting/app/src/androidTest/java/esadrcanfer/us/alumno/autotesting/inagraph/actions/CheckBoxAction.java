package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class CheckBoxAction extends Action {
    String targetText;
    String targetClass;
    public CheckBoxAction(UiObject target) {
        super(target);
        targetText="";
        targetClass="";
    }

    @Override
    public void perform() throws UiObjectNotFoundException {
        targetText=target.getText();
        targetClass=target.getClassName();
        this.target.click();
    }

    @Override
    public String toString() {
        return "Click Radio Button " + targetText;
    }
}
