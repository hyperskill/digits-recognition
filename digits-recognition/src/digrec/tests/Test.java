package digrec.tests;


import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Test implements Serializable {

	private static final long serialVersionUID = -8385655899811016412L;
	  
	  private final int a;
	  private final transient int b;
	  
	  public Test() {
	    a = 1;
	    b = 1;
	  }
	  
	  @Override
	  public String toString() { return a + " " + b; }
	  
	public static void main(String[] args) {
		Child child = new Child();
	    // Function that serializes and deserializes object back and forth
		FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try
	    {
	        fos = new FileOutputStream("test");
	        out = new ObjectOutputStream(fos);
	        out.writeObject(child);
	        out.close();
	    }
	    catch(IOException ex)
	    {
	        ex.printStackTrace();
	    }

	    FileInputStream fis = null;
	       ObjectInputStream in = null;
	       try
	       {
	             fis = new FileInputStream("test");
	             in = new ObjectInputStream(fis);
	           child = (Child)in.readObject();
	             in.close();
	           }
	       catch(IOException ex)
	       {
	             ex.printStackTrace();
	           }
	       catch(ClassNotFoundException ex)
	       {
	             ex.printStackTrace();
	           }
	    
	    System.out.println(child);
	}

	

	
}

class Child extends Test {
	  private static final long serialVersionUID = 7184464867105332396L;
	}
