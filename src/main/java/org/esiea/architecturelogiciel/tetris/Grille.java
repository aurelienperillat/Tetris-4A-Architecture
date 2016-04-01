package org.esiea.architecturelogiciel.tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Grille extends JPanel {
	
	private int [][] grille;
	private int score = 0;
	public static final long serialVersionUID = 1234567890;
	
	public Grille(){
		grille = new int [12][21];
		
		for(int i=0; i<grille.length; i++){
			for(int j=0; j<grille[i].length; j++){
				if(i == 0 || i == 11) grille[i][j] = 9;
				else if (j == 20) grille[i][j] = 9;
				else grille[i][j] = 0;
 			}
		}	
	}
	
	public void paintComponent(Graphics g) {
		for(int i=0; i<grille.length; i++){
			for(int j=0; j<grille[i].length; j++){
				if(grille[i][j] == 0){
					g.setColor(new Color(250,250,250));
					g.fillRect(i*30, j*30, 30, 30);
				}
				
				else {
					switch (grille[i][j]) {
                    	case 1:     g.setColor(new Color(0,192,0));       g.fill3DRect(i*30, j*30, 30, 30, true); break; //vert
                    	case 2:     g.setColor(Color.pink);               g.fill3DRect(i*30, j*30, 30, 30, true); break; //rouge
                    	case 3:     g.setColor(new Color(0,128,224));     g.fill3DRect(i*30, j*30, 30, 30, true); break; //bleu
                    	case 4:     g.setColor(new Color(0,192,192));     g.fill3DRect(i*30, j*30, 30, 30, true); break; //cyan
                    	case 5:     g.setColor(Color.orange);             g.fill3DRect(i*30, j*30, 30, 30, true); break; //orange
                    	case 6:     g.setColor(Color.darkGray);           g.fill3DRect(i*30, j*30, 30, 30, true); break; //gris
                    	case 7:     g.setColor(Color.magenta);            g.fill3DRect(i*30, j*30, 30, 30, true); break; //magenta
                    	case 9:     g.setColor(new Color(200,200,200));              g.fill3DRect(i*30, j*30, 30, 30, true); break;
					}
				}
			}
		}	
	}
	
	public void addPiece(int [][] currentPiece, int posX, int posY, int position){
		int i,j,k=0;
		
		if((posY+3) > 20){
			for(j=posY; j < 21; j++){	
				for(i=posX; i<(posX+4); i++){	
					if(grille[i][j] == 0)
						grille[i][j] = currentPiece[position][k];
					k++;
				}
			}
		}
		else if(posX < 0){
			for(j=posY; j<(posY+4); j++){
				
				k = k - posX;
				for(i=posX; i>0; i--){				
					if(grille[i][j] == 0)
						grille[i][j] = currentPiece[position][k];
					k++;
				}
			}
		}
		else if((posX+3) > 11){
			for(j=posY; j<(posY+4); j++){
				for(i=posX; i<12; i++){
					if(grille[i][j] == 0)
						grille[i][j] = currentPiece[position][k];
					k++;
				}
				k = k + (4 - (12 - posX));
			}
		}
		else {
			for(j=posY; j<(posY+4); j++){
				for(i=posX; i<(posX+4); i++){
					if(grille[i][j] == 0)
						grille[i][j] = currentPiece[position][k];
					k++;
				}
			}
		}
	}
	
	public void removePiece(int [][] currentPiece, int posX, int posY, int position) {
		int i,j,k=0;
		
		if((posY+3) > 20){
			for(j=posY; j < 21; j++){	
				for(i=posX; i<(posX+4); i++){
					if(currentPiece[position][k] != 0)
						grille[i][j] = 0;
					k++;
				}
			}
		}	
		else if(posX < 0){
			for(j=posY; j<(posY+4); j++){
				k = k - posX;
				for(i=posX; i>0; i--){	
					if(currentPiece[position][k] != 0)
						grille[i][j] = 0;
					k++;
				}
			}
		}
		else if((posX+3) > 11){
			for(j=posY; j<(posY+4); j++){
				for(i=posX; i<12; i++){
					if(currentPiece[position][k] != 0)
						grille[i][j] = 0;
					k++;
				}
				k = k + (4 - (12 - posX));
			}
		}
		else {
			for(j=posY; j<(posY+4); j++){
				for(i=posX; i<(posX+4); i++){
					if(currentPiece[position][k] != 0)
						grille[i][j] = 0;
					k++;
				}
			}
		}
	}
	
	public void removeFullLines(int [][] currentPiece, int posX, int posY, int position) {
		int i,j;
		boolean needToRemove;
		
		if((posY+3) > 19){
			for(j=posY; j<20; j++){
				needToRemove = true;
				
				for(i=1; i<11; i++){
					if(grille[i][j] == 0)
						needToRemove = false;
				}
				
				if(needToRemove == true){
					for(int l=j; l>1; l--){
						for(i=1; i<11; i++){
							grille[i][l] = grille[i][l-1];
						}
					}
					score ++;
				}
			}
		}

		else {
			for(j=posY; j<(posY+4); j++){
				needToRemove = true;
				
				for(i=1; i<11; i++){
					if(grille[i][j] == 0)
						needToRemove = false;
				}
				
				if(needToRemove == true){
					for(int l=j; l>1; l--){
						for(i=1; i<11; i++){
							grille[i][l] = grille[i][l-1];
						}
					}
					score ++;
				}
			}
		}
	}
	
	public void cleanGrille() {
		for(int i=1; i<11; i++)
			for(int j=0; j<20; j++)
				grille[i][j] = 0;
	}
	
	public boolean isFullLine(int [][] currentPiece, int posX, int posY, int position) {
		int i,j;
		boolean needToRemove;
		
		if((posY+3) > 19){
			for(j=posY; j<20; j++){
				needToRemove = true;
				
				for(i=1; i<11; i++){
					if(grille[i][j] == 0)
						needToRemove = false;
				}
				
				if(needToRemove == true){
					return true;
				}
			}
		}
		else {
			for(j=posY; j<(posY+4); j++){
				needToRemove = true;
				
				for(i=1; i<11; i++){
					if(grille[i][j] == 0)
						needToRemove = false;
				}
				
				if(needToRemove == true){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean canLineDown(int [][] currentPiece, int posX, int posY, int position) {
		posY ++;
		boolean isEmpty = isEmpty(currentPiece,posX,posY,position);
		return isEmpty;
	}
	
	public boolean canMoveLeft(int [][] currentPiece, int posX, int posY, int position) {
		posX --;
		boolean isEmpty = isEmpty(currentPiece,posX,posY,position);
		return isEmpty;
	}
	
	public boolean canMoveRight(int [][] currentPiece, int posX, int posY, int position) {
		posX ++;
		boolean isEmpty = isEmpty(currentPiece,posX,posY,position);
		return isEmpty;
	}
	
	public boolean canTurn(int [][] currentPiece, int posX, int posY, int position){
		position ++;
		boolean isEmpty = isEmpty(currentPiece,posX,posY,position);
		return isEmpty;
	}
	
	public boolean isEmpty(int [][] currentPiece, int posX, int posY, int position){
		int i,j,k=0;
		
		if((posY+3) > 20){
			for(j=posY; j < 21; j++){	
				for(i=posX; i<(posX+4); i++){
					if(grille[i][j] != 0 && currentPiece[position][k] != 0)
						return false;
					k++;
				}
			}
		}
		
		else if(posX < 0){
			for(j=posY; j<(posY+4); j++){
				
				k = k - posX;
				for(i=0; i<(posX+4); i++){	
					if(grille[i][j] != 0 && currentPiece[position][k] != 0)
						return false;
					k++;
				}
				System.out.println();
			}
		}
		
		else if((posX+3) > 11){
			for(j=posY; j<(posY+4); j++){
				for(i=posX; i<12; i++){
					if(grille[i][j] != 0 && currentPiece[position][k] != 0)
						return false;
					k++;
				}
				k = k + (4 - (12 - posX));
			}
		}
		
		else {
			for(j=posY; j<(posY+4); j++){
				for(i=posX; i<(posX+4); i++){
					if(grille[i][j] != 0 && currentPiece[position][k] != 0)
						return false;
					k++;
				}
			}
		}
		
		return true;
	}
	
	public boolean isGameOver(int [][] currentPiece, int posX, int posY, int position){
		int i,j,k=0;
		
		for(i=posX; i<(posX+4); i++){
			for(j=posY; j<(posY+4); j++){
				if(grille[i][j] != 0 && currentPiece[position][k] != 0)
					return true;
				k++;
			}
		}
		
		return false;
	}
	
	public int getScore() { return this.score; }
	public void resetScore() { this.score = 0; }
}
