package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import net.sf.extjwnl.JWNLException;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public abstract class InputGenerator {
    public abstract void generateInput(UiObject object) throws UiObjectNotFoundException;
}
