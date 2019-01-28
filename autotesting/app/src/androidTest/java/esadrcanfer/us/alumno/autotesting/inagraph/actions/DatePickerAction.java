package esadrcanfer.us.alumno.autotesting.inagraph.actions;
import androidx.test.uiautomator.UiObject;

class DatePickerAction extends InputAction {
    public DatePickerAction(UiObject target, InputGenerator generator) {
        super(target, generator);
    }

    @Override
    public String toString() {
        return "introducing new date";
    }
}
