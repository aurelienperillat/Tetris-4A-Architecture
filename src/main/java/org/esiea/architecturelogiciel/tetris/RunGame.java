package org.esiea.architecturelogiciel.tetris;

import javax.swing.event.EventListenerList;

public class RunGame implements Runnable{
	private Grille grille;
    private Piece pieceGetter;
    private int [][] currentPiece;
    private int posX,posY,position;
    private final EventListenerList listeners = new EventListenerList();
    
    public RunGame(){
    	grille = new Grille();
    	pieceGetter = new Piece();
    	currentPiece = new int[4][16];
    }
    
	public void run() {
		boolean needPiece = true;
		boolean down;
		
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
	
	public void addGameOverListener(GameOverListener listener){
		listeners.add(GameOverListener.class, listener);
	}
	
	public GameOverListener[] getGameOverListeners() {
		return listeners.getListeners(GameOverListener.class);
	}
	
	protected void fireGameOver() {
		for(GameOverListener listener : getGameOverListeners()){
			listener.gameIsOver();
		}
	}
	
	public Grille getGrille() { return grille; }
	public int[][] getCurrentPiece() { return currentPiece; }
	public int getPosX() { return posX; }
	public int getPosY() { return posY; }
	public int getPosition() { return position; }

}
