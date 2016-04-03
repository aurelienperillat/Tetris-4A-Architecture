package org.esiea.architecturelogiciel.tetris;

import java.io.BufferedReader;
import java.io.IOException;

public class Reception implements Runnable{
	private BufferedReader in;
	private RunGameMulti runGame;
	
	public Reception(BufferedReader in , RunGameMulti runGame){
		this.in = in;
		this.runGame = runGame;
	}
	
	public void run(){
		int type = 1;
		while(true) {
			try {
				String message = in.readLine();
				System.out.println(message);
				System.out.println(type);
				if(type == 1)
					runGame.fallDown();
				if(type == 2){
					runGame.changePiece();
					type = 0;
				}
				
				type ++;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
