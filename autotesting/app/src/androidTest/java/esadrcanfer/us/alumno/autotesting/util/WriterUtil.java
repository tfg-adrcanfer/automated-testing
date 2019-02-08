package esadrcanfer.us.alumno.autotesting.util;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriterUtil {
	private File logFile;
	
	public WriterUtil() {
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String filename = "TestCase-" + timeLog+".txt";
		this.logFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
	}
	
	public File getLogFile() {
		return logFile;
	}
	
	public void write(String text) {
		try {
			FileOutputStream fos = new FileOutputStream(getLogFile(), true);
			fos.write(text.getBytes());

			fos.close();
			Log.d("TFG", "Saved!");

		} catch (FileNotFoundException e){
			e.printStackTrace();
			Log.d("TFG", "File not found!");
		} catch (IOException e){
			e.printStackTrace();
			Log.d("TFG", "Error saving!");
		}
	}
	
	public String getPath() {
		String path = "";
		try {
			path = logFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	
	

}
