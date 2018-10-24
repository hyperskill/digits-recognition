package digrec.iopack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class Reader {
	String name;
	private byte[] storage;
	
	Reader(String path) throws IOException {
		name = path;
		Path pth = Paths.get(path);
				if(Files.exists(pth)) {
					Test.LOG.log(Level.FINE, "Файл найден");
					this.storage = Files.readAllBytes(pth);
				}
				else {
					throw new FileNotFoundException("Файл с именем \""+ name + "\" не найден." );
				}
	}
	
	Reader(String path, String name) throws IOException{
		this(path);
		this.name = name;
	}

	public byte[] getStorage() {
		return storage;
	}

	
	
	
}
             