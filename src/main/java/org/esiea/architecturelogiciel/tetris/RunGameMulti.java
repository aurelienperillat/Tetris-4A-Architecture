package org.esiea.architecturelogiciel.tetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.event.EventListenerList;

public class RunGameMulti implements Runnable{
	private Grille grille;
    private Piece pieceGetter;
    private int [][] currentPiece;
    private int posX,posY,position;
    private final EventListenerList listeners = new EventListenerList();
    private BufferedReader in;
	private PrintWriter out;
	private Thread t;
    
    public RunGameMulti(Socket s){
    	grille = new Grille();
    	pieceGetter = new Piece();
    	currentPiece = new int[4][16];
    	
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public void run() {
		boolean needPiece = true;
		boolean down;
		t = new Thread(new Reception(in,this));
		t.start();
		
		while (true){
			
			if (needPiece){
				pieceGetter.getPiece(currentPiece);
				posX = 4; posY = 0; position = 0;
				
				if(grille.isGameOver(currentPiece, posX, posY, position) == true){
					System.out.println("Game Over !");
					System.out.println(grille.getScore());
					fireGameOver();
					break;
				}
				
				grille.addPiece(currentPiece, posX, posY, position);
				needPiece = false;
			}
			
			if(grille.isFullLine(currentPiece, posX, posY, position) == true) {
				out.println("one line full");
				out.flush();
			}
			
			grille.removePiece(currentPiece, posX, posY, position);
			down = grille.canLineDown(currentPiece, posX, posY, position);
			
			if(down == true){
				posY++;
				grille.addPiece(currentPiece, posX, posY, position);
			}
			
			if(down == false){
				grille.addPiece(currentPiece, posX, posY, position);
				grille.removeFullLines(currentPiece, posX, posY, position);
				needPiece = true;
			}
			
			grille.repaint();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void moveLeft(){
		grille.removePiece(currentPiece, posX, posY, position);
		boolean can = grille.canMoveLeft(currentPiece, posX, posY, position);
		
		if(can == true){
			posX--;
			grille.addPiece(currentPiece, posX, posY, position);
		}
		else
			grille.addPiece(currentPiece, posX, posY, position);
	}
	
	public void moveRight() {
		grille.removePiece(currentPiece, posX, posY, position);
		boolean can = grille.canMoveRight(currentPiece, posX, posY, position); 
		
		if(can == true){
			posX++;
			grille.addPiece(currentPiece, posX, posY, position);
		}
		else
			grille.addPiece(currentPiece, posX, posY, position);
	}
	
	public void turn() {
		grille.removePiece(currentPiece, posX, posY, position);
		boolean can = grille.canTurn(currentPiece, posX, posY, position); 
		
		if(can == true){
			position++;
			position = position % 4;
			grille.addPiece(currentPiece, posX, posY, position);
		}
		else
			grille.addPiece(currentPiece, posX, posY, position);
	}
	
	public void fallDown() {
		boolean can = true;
		
		while(can == true){
			grille.removePiece(currentPiece, posX, posY, position);
			can = grille.canLineDown(currentPiece, posX, posY, position);
			
			if(can == true){
				posY++;
				grille.addPiece(currentPiece, posX, posY, position);
			}
		}

		grille.addPiece(currentPiece, posX, posY, position);
	}
	
	public void changePiece() {
		grille.removePiece(currentPiece, posX, posY, position);
		pieceGetter.getPiece(currentPiece);
		grille.addPiece(currentPiece, posX, posY, position);
	}
	
	public void addGameOverListener(GameOverListener listener){
		listeners.add(GameOverListener.class, listener);
	}
	
	public GameOverListener[] getGameOverListeners() {
		return listeners.getListeners(GameOverListener.class);
	}
	
	protected void fireGameOver() {
		for(GameOverListener listener : getGameOverListeners()){
			listener.gameMultiIsOver();
		}
	}
	
	public Grille getGrille() { return grille; }
	public int[][] getCurrentPiece() { return currentPiece; }
	public int getPosX() { return posX; }
	public int getPosY() { return posY; }
	public int getPosition() { return position; }
	public Thread getThread() { return t; }

}