package com.example.demo.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.example.demo.Game.Game;
import com.example.demo.Pieces.Piece;
import com.example.demo.Player.Player;

public class Board implements Cloneable {
	public static final int ROWS = 8;
	public static final int COLUMNS = 8;
	
	public int[][] grid;
	public Piece[][] pieces;
	public Movement lastMove;
	public Piece captured;
	Player players;
	
	public Stack<Movement> lastMoves = new Stack<>();
	public Stack<Piece> capturedPieces = new Stack<>();
	public List<Piece> listPieces = new ArrayList<Piece>();
	
	public Board() {
		grid = new int[ROWS][COLUMNS];
		pieces = new Piece[ROWS][COLUMNS];
	}
	
	public void setPieceIntoBoard(int x, int y, Piece piece) {
		if(piece != null) {
			grid[x][y] = piece.getValueInBoard();
			pieces[x][y] = piece;
			listPieces.add(piece);
		}
		else {
			grid[x][y] = 0;
			pieces[x][y] = null;
		}
	}
	
	public void updatePieces(int fromX, int fromY, int toX, int toY, Piece piece) {
		lastMove = new Movement(fromX, fromY, toX, toY, piece);
		lastMoves.add(lastMove);
		if(pieces[toX][toY] != null) {
			captured = pieces[toX][toY];
			capturedPieces.add(captured);
			players.addKill();
			listPieces.remove(captured);
			Game.allPiece.remove(captured);
			Game.fillPieces();
		}
		else {
			capturedPieces.add(null);
			players.subKill();
		}
		
		grid[fromX][fromY] = 0;
		grid[toX][toY] = piece.getValueInBoard();
		pieces[fromX][fromY] = null;
		pieces[toX][toY] = piece;
	}
	
	public Piece getPiece(int x, int y) {
		return pieces[x][y];
	}
	
	public void setXY(int x,int y,int v) {
		 grid[x][y] = v ;
	}

	public int getXY(int xCord, int yCord) {
		return grid[xCord][yCord];
	} 
	
	public int[][] getGrid(){
		return grid;
	}
	
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}
	
	public Board getNewBoard() {
		Board b = new Board();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(this.getPiece(i, j) != null) {
					b.setPieceIntoBoard(i, j, this.getPiece(i, j).getClone());
				}
			}
		}
		return b;
	}
}
