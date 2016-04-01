import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Transmission implements Runnable{
	private BufferedReader in;
	private PrintWriter out;
	private String message = null;
	
	public Transmission(BufferedReader in, PrintWriter out){ 
		this.in = in; 
		this.out = out;
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			message = in.readLine();
			out.println(message);
			out.flush();
			System.out.println(message);
			
		    } catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

}
