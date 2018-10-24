package iopack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

	public static void main(String[] args) throws IOException  {
		loggerConfig (Level.ALL);
		ImageSourse trainImage = new ImageSourse("./assets/train-images.idx3-ubyte");
	}
	
	
	static void loggerConfig (Level lv) {
		final Logger LOG = Logger.getLogger(ImageSourse.class.getName()) ;
		LOG.setLevel(lv);
		Handler h = new ConsoleHandler();
		h.setLevel(lv);
		LOG.addHandler(h);
	}

}
