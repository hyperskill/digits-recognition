package iopack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader {
	String name;
	byte[] storage;
	
	Reader(String path) {
		name = path;
		try {
			this.storage = Files.readAllBytes(Paths.get(path));
		}catch(IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	Reader(String path, String name){
		this.name = name;
		try {
			this.storage = Files.readAllBytes(Paths.get(path));
		}catch(IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	
}
             