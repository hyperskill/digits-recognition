package digrec.iopack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageSourse {
	private int imgCount;
	private int cellsCount;
	private byte[] pixels;
	//private  static final Logger LOG = Logger.getLogger("digrec");//(ImageSourse.class.getName()) ;
	
	ImageSourse(String path) throws IOException {
		
		Reader file = new Reader(path);
		int byteCount;
		try(DataInputStream dist = new DataInputStream(new ByteArrayInputStream(file.getStorage()))) {
				dist.skipBytes(4);
				imgCount = dist.readInt();
				
				cellsCount = dist.readInt()*dist.readInt();
				byteCount = imgCount*cellsCount;
				Test.LOG.log(Level.FINE, "\timgCount = {0}\n\tcellsCount = {1}\n\tbyteCount = {2}", new Object[] {imgCount, cellsCount, byteCount});
				pixels = new byte[byteCount];
				int res = dist.read(pixels);
				Test.LOG.log(Level.FINE, "read в массив вернуло {0}; размер массива {1} байт",new Object[] {res, pixels.length});
				if (res!= byteCount) {
					
					Test.LOG.log(Level.WARNING,"Некорректно прочитаны данные из файла {0}\n\tКол-во прочитанных байтов = {1} из {2}",new Object[] {path,res,byteCount});
				}
		}
	}
	
}
