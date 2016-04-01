package org.esiea.architecturelogiciel.tetris;

import java.util.EventListener;

public interface GameOverListener extends EventListener{
	void gameIsOver();
	void gameMultiIsOver();
	void connected();
}
