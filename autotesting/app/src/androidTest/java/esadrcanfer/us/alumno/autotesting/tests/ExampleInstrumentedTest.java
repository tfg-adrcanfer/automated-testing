package esadrcanfer.us.alumno.autotesting.tests;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static java.lang.Thread.sleep;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String tag = "Testing";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        //Context appContext = InstrumentationRegistry.getTargetContext();

        //assertEquals("com.example.testingandroid", appContext.getPackageName());
        UiDevice mDevice;
        mDevice = UiDevice.getInstance(getInstrumentation());
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps"));
        allAppsButton.click();
        UiScrollable appViews = new UiScrollable(
                new UiSelector().scrollable(true));
        appViews.scrollIntoView(new UiSelector().text("TestingAndroid"));
        UiObject testingApp = mDevice.findObject(new UiSelector().text("TestingAndroid"));
        testingApp.clickAndWaitForNewWindow();
        BySelector sel = By.clazz("android.widget.Button");
        List<UiObject2> botones = mDevice.findObjects(sel);
        for (UiObject2 boton: botones) {
            boton.click();
            String message = "Se ha pulsado: " + boton.getText();
            Log.i(tag, message);
            sleep(2500);

        }
    }
}
