package esadrcanfer.us.alumno.autotesting.inagraph.actions;


import androidx.test.uiautomator.UiObject;

public class RadioButtonAction extends InputAction {
    public RadioButtonAction(UiObject target, InputGenerator generator) {
        super(target, generator);
    }

    @Override
    public String toString() {
        return "click on radio button";
    }

}
