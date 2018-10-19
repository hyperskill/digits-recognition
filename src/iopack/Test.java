package iopack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Test {

	public static void main(String[] args) throws IOException  {
		Reader file = new Reader("./assets/train-images.idx3-ubyte");
		try(DataInputStream dist = new DataInputStream(new ByteArrayInputStream(file.storage))) {
				System.out.println(dist.readInt());
				
		}
	}

}
