package iopack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ImageSourse {
	private int imgCount;
	private int rowsCount;
	private int colsCount;
	private byte[] pixels;
	
	ImageSourse(String path) throws IOException {
		Reader file = new Reader(path);
		try(DataInputStream dist = new DataInputStream(new ByteArrayInputStream(file.getStorage()))) {
				dist.readInt();
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
