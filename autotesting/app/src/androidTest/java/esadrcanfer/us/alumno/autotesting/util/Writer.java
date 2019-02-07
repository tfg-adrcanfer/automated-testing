package esadrcanfer.us.alumno.autotesting.util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Writer {
	private File logFile;
	
	public Writer() {
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		this.logFile = new File("/src/test/resources/TestCase " + timeLog);
	}
	
	public File getLogFile() {
		return logFile;
	}
	
	public void write(String text) {
		BufferedWriter writer = null; 
		try {
			writer = new BufferedWriter(new FileWriter(getLogFile(), true));
            writer.append(text);
            writer.newLine();
		} catch (IOException e) {
		    Log.d("TFG", "Se ha producido una excepción al escribir en el fichero");
			e.printStackTrace();
		} finally {
			try {
			    if(writer!= null){
                    writer.close();
                }
			} catch (IOException e) {
                Log.d("TFG", "Se ha producido una excepción al cerrar el fichero");
				e.printStackTrace();
			}
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
