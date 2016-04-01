package org.esiea.architecturelogiciel.tetris;

import java.io.BufferedReader;
import java.io.IOException;

public class Reception implements Runnable{
	public static BufferedReader in;
	private RunGameMulti runGame;
	
	public Reception(BufferedReader in , RunGameMulti runGame){
		this.in = in;
		this.runGame = runGame;
	}
	
	public void run(){
		while(true) {
			try {
				String message = in.readLine();
				System.out.println(message);
				runGame.fallDown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
