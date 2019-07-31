package digrec.stages.stage5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class Assets implements Serializable {
	private static final long serialVersionUID = 328779L;
	
	private transient final Path ZIP_PATH = Paths.get("assets","data.zip"); 
	public int[] inputSample = new int[785];
	public int[][] trainingSamples = new int [70000][785];
	private int n=-1;
	
	public void fillTrainingSamples () {
		System.out.println("Reading...");
		try(FileSystem zipFileSys = FileSystems.newFileSystem(ZIP_PATH, null)) {
			
			for(Path path:zipFileSys.getRootDirectories()) {
				Files.walkFileTree(path, new SimpleFileVisitor <Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						n++;
						Scanner sc = new Scanner (file);
						for(int i = 0; i<785; i++) {
							trainingSamples[n][i] = sc.nextInt();
						}
						sc.close();
						return FileVisitResult.CONTINUE;
					}
				});
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		saveToF();
	}
	
	public void getinputSample (int fileNumber){
		String fileNum = String.valueOf(fileNumber);
		while(fileNum.length()<5) {
			fileNum="0"+fileNum;
		}
		
		try(FileSystem zipFileSys = FileSystems.newFileSystem(ZIP_PATH, null)) {
			Path path = zipFileSys.getPath("/data/" + fileNum + ".txt");
			Scanner sc = new Scanner (path);
			for(int i = 0; i<785; i++) {
				inputSample[i] = sc.nextInt();
			}
			sc.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void saveToF() {
		 
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("base5.bin"))) {
			out.writeObject(this);
			System.out.println("Saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	public Assets loadFromF() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("base5.bin"))) {
			Assets as = (Assets)in.readObject();
			System.out.println("Loaded successfully.");	
			return as;
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
