package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;

public class ActionFactory {

    public static Map<UiObject, Action> createInputActions(UiDevice device) {
        TextInputGenerator generator = new TextInputGenerator();
        List<UiObject> inputTexts = ElementIdentifier.findElements(device, "android.widget.EditText");
        Map<UiObject, Action> result = new HashMap<>();
        for (UiObject input : inputTexts) {
            result.put(input, new TextInputAction(input, generator));
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

    public static Map<UiObject, Action> createActions(UiDevice device) {
        Map<UiObject, Action> actions = new HashMap<>();
        actions.putAll(ActionFactory.createButtonActions(device));
        actions.putAll(ActionFactory.createInputActions(device));
        actions.putAll(ActionFactory.createCheckBoxActions(device));
        actions.putAll(ActionFactory.createRadioActions(device));
        return actions;
    }
}
