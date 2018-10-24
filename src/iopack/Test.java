package iopack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Test {

	public static void main(String[] args) throws IOException  {
		Reader file = new Reader("./assets/train-images.idx3-ubyte");
		try(DataInputStream dist = new DataInputStream(new ByteArrayInputStream(file.getStorage()))) {
				System.out.println(dist.readInt());
				System.out.println(dist.readInt());
				System.out.println(dist.readInt());
				System.out.println(dist.readInt());
				int r;
				for(int i = 0; i<(28); i++) {
					for (int j = 0; j<28;j++) {
					System.out.print((r = dist.read())==0?"  ":r+" ");
					}
					System.out.println();
				}
				
		}
	}

}
