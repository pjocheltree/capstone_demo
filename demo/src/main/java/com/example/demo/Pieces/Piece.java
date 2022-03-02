package com.example.demo.Pieces;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Board.Board;
import com.example.demo.Board.Movement;
import com.example.demo.Game.Game;

public abstract class Piece implements Cloneable {
	protected int xCord;
	protected int yCord;
	protected boolean isWhite;
	protected boolean isAlive;
	protected int valueInTheBoard;
	protected Board board;
	static int size = 80;
	protected List<Movement> moves = new ArrayList<>();
	
	public boolean makeMove(int toX, int toY, Board board) {
		Movement move = new Movement(xCord, yCord, toX, toY, this);
		if(!alive()) {
			return false;
		}
		for(Movement m: moves) {
			if(m.compareTo(move) == 0) {
				board.updatePieces(xCord, yCord, toX, toY,this);
				xCord = toX;
				yCord = toY;
				return true;
			}
		}
		return false;
		
	}
	
	public abstract boolean canMove(int x, int y, Board board);

	@SuppressWarnings("unlikely-arg-type")
	public boolean alive() {
		if(board.getXY(xCord, yCord) != valueInTheBoard || board.getXY(xCord, yCord) == 0 || board.getPiece(xCord, yCord) == null) {
			isAlive = false;
			Game.allPiece.remove(getClass());
		}
		return isAlive;
	}
	
	public void getPseudoLegalMoves(Board b) {
		moves = new ArrayList<Movement>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(canMove(i, j, b)) {
					moves.add(new Movement(xCord, yCord, i, j, this));
				}
			}
		}
	}
	
	public void initializeSide(int value) {
		valueInTheBoard = value;
	}
	
	public int getxCord() {
		return xCord;
	}
	
	public void setxCord(int xcord) {
		this.xCord = xcord;
	}
	
	public int getyCord() {
		return yCord;
	}
	
	public void setyCord(int ycord) {
		this.yCord = ycord;
	}
	
	public boolean isWhite() {
		return isWhite;
	}
	
	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}
	
	public List<Movement> getMoves() {
		return moves;
	}
	
	public void setMoves(List<Movement> moves) {
		this.moves = moves;
	}
	
	public void setValueInBoard(int value) {
		this.valueInTheBoard = value;
	}
	
	public int getValueInBoard() {
		return valueInTheBoard;
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Piece getClone() {
		try {
			return (Piece) this.clone();
		}
		catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
