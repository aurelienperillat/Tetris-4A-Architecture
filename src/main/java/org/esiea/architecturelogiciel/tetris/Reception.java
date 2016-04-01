package org.esiea.architecturelogiciel.tetris;

import java.io.BufferedReader;
import java.io.IOException;

public class Reception implements Runnable{
	public static BufferedReader in;
	
	public Reception(BufferedReader in){
		this.in = in;
	}
	
	public void run(){
		while(true) {
			try {
				String message = in.readLine();
				System.out.println(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
