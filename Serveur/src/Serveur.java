import java.io.*;
import java.net.*;

public class Serveur {
	public static ServerSocket socketserver  ;
	public static Socket socket ;
	public static BufferedReader in;
	public static PrintWriter out;
	public static Thread t1;

	public static void main(String[] zero) {
		
		try {
			socketserver = new ServerSocket(2009,2);
			System.out.println("Le serveur est à l'écoute du port "+socketserver.getLocalPort());
			
			while(true){
				socket = socketserver.accept();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());
				String message = in.readLine();
				System.out.println(message+" connected");
				
				t1 = new Thread(new Transmission(in,out));
				t1.start();
		
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}