package esadrcanfer.us.alumno.autotesting.tests;

import android.os.health.UidHealthStats;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class ReadTestCase {

    @Test
    public void read1() throws UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("TestCase-20190208_131831.txt");
        TestCase testCase = readUtil.generateTestCase();
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        //testCase.executeAfter();
        Log.d("TFG","Done!");
    }

    @Test
    public void read2() throws UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("TestCase-20190209_125318.txt");
        TestCase testCase = readUtil.generateTestCase();
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        //testCase.executeAfter();
        Log.d("TFG","Done!");
    }
}
