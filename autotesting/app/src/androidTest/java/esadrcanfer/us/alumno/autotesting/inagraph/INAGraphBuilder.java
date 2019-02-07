package esadrcanfer.us.alumno.autotesting.inagraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ActionFactory;

public class INAGraphBuilder {

    private static INAGraphBuilder _instance;

    private INAGraphBuilder() {
    }

    public static INAGraphBuilder getInstance() {
        if (_instance == null)
            _instance = new INAGraphBuilder();
        return _instance;
    }

    public INAGraph build(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        startApp(device, appPackage);
        Node node = buildNode(device);
        closeApp(device, appPackage);
        return new INAGraph(node);
    }

    private void closeApp(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        CloseAppAction action = new CloseAppAction(appPackage);
        action.perform();
    }

    private void startApp(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        StartAppAction action = new StartAppAction(appPackage);
        action.perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    public Node buildNode(UiDevice device) throws UiObjectNotFoundException {
        Node node = new Node();
        createActions(node, device);
        buildVertex(node, device);
        return node;
    }

    public void createActions(Node node, UiDevice device) {
        Map<UiObject, Action> actions = ActionFactory.createActions(device);
        node.getControls().addAll(actions.keySet());
        node.getAvailableActions().addAll(actions.values());
    }


    public void buildVertex(Node node, UiDevice device) throws UiObjectNotFoundException {
        for (Action a : node.getAvailableActions()) {
            try {
                a.perform();
                if (!isSameNode(node, device)) {
                    Node nextNode = buildNode(device);
                    node.getOutputVertex().put(a, nextNode);
                    Action goBack = new GoBackAction(device);
                    nextNode.getOutputVertex().put(goBack, node);
                    goBack.perform();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clearly, this implementation is simplistic and can lead to inconsistent behaviour when applied
     * to complex user interfaces, but for a first approach it could be a starting point.
     *
     * @param currentNode
     * @param device
     * @return Whether we are in a new UI state or not.
     * @throws UiObjectNotFoundException
     */

    public boolean isSameNode(Node currentNode, UiDevice device) throws UiObjectNotFoundException {
        boolean result = true;
        List<Action> actions = new ArrayList<>(ActionFactory.createActions(device).values());
        for (int i = 0; i < actions.size() && result; i++)
            result = (result && currentNode.getAvailableActions().contains(actions.get(i)));
        result = (result && currentNode.getAvailableActions().size() == (actions.size()));
        return result;
    }

}
