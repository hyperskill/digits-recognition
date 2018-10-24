package digrec.iopack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Test {
	public static final Logger LOG = Logger.getLogger("digrec");
	
	public static void main(String[] args) throws IOException  {
		
		loggerConfig (Level.ALL);
		ImageSourse trainImage = new ImageSourse("./assets/train-images.idx3-ubyte");
	}
	
	
	static void loggerConfig (Level lv) throws SecurityException, IOException {
		//LogManager.getLogManager().addLogger(LOG);
		LogManager.getLogManager().readConfiguration(
                Test.class.getResourceAsStream("/logging.properties"));
		
		LOG.setLevel(lv);
		/*Handler h = new FileHandler( );
		h.setFormatter(new SimpleFormatter());
		h.setLevel(Level.ALL);
		LOG.addHandler(h);*/
	}

}
