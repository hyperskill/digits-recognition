package digrec.iopack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LabelSourse {
	private int lblCount;
	private byte[] labels;
	
	
	LabelSourse(String path) throws IOException {
		
		Reader file = new Reader(path);
		try(DataInputStream dist = new DataInputStream(new ByteArrayInputStream(file.getStorage()))) {
				dist.skipBytes(4);
				lblCount = dist.readInt();
				Test.LOG.log(Level.FINE, "\tlblCount = {0}", lblCount);
				labels = new byte[lblCount];
				int res = dist.read(labels);
				Test.LOG.log(Level.FINE, "read в массив вернуло {0}; размер массива {1} байт",new Object[] {res, labels.length});
				if (res!= lblCount) {
					
					Test.LOG.log(Level.WARNING,"Некорректно прочитаны данные из файла {0}\n\tКол-во прочитанных байтов = {1} из {2}",new Object[] {path,res,lblCount});
				}
		}
	}
}




