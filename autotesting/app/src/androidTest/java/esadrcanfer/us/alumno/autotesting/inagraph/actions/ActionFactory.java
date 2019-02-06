package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import esadrcanfer.us.alumno.autotesting.inagraph.Node;

public class ActionFactory {

    public static Map<UiObject, Action> createInputActions(UiDevice device) {
        TextInputGenerator generator = new TextInputGenerator();
        List<UiObject> inputTexts = ElementIdentifier.findElements(device, "android.widget.EditText");
        Map<UiObject, Action> result = new HashMap<>();
        for (UiObject input : inputTexts) {
            result.put(input, new InputAction(input, generator));
        }
        return result;
    }

    public static Map<UiObject, Action> createButtonActions(UiDevice device) {
        Map<UiObject, Action> result = new HashMap<>();
        List<UiObject> buttons = ElementIdentifier.findElements(device, "android.widget.Button");
        for (UiObject input : buttons) {
            result.put(input, new ButtonAction(input));
        }
        return result;
    }

    public static Map<UiObject, Action> createRadioActions(UiDevice device) {
        RadioButtonInputGenerator generator = new RadioButtonInputGenerator();
        Map<UiObject, Action> result = new HashMap<>();
        List<UiObject> buttons = ElementIdentifier.findElements(device, "android.widget.RadioGroup");
        for (UiObject input : buttons) {
            result.put(input, new RadioButtonAction(input, generator));
        }
        return result;
    }

    public static Map<UiObject, Action> createCheckBoxActions(UiDevice device) {
        Map<UiObject, Action> result = new HashMap<>();
        List<UiObject> buttons = ElementIdentifier.findElements(device, "android.widget.CheckBox");
        for (UiObject input : buttons) {
            result.put(input, new CheckBoxAction(input));
        }
        return result;

    }

    public static List<Action> createSelectsActions(Node node, UiDevice device) {
        SpinnerInputGenerator generator = new SpinnerInputGenerator(device);
        List<Action> result = new ArrayList<>();
        List<UiObject> buttons = ElementIdentifier.findElements(device, "android.widget.Spinner");
        for (UiObject input : buttons) {
            result.add(new SpinnerAction(input, generator));
            node.getControls().add(input);
        }
        return result;
    }

    public static List<Action> createDatesActions(Node node, UiDevice device) {
        DatePickerInputGenerator generator = new DatePickerInputGenerator();
        List<Action> result = new ArrayList<>();
        List<UiObject> buttons = ElementIdentifier.findElements(device, "android.widget.DatePicker");
        for (UiObject input : buttons) {
            result.add(new DatePickerAction(input, generator));
            node.getControls().add(input);
        }
        return result;

    }
}
