package com.example.demo.Pieces;

import com.example.demo.Board.*;
import com.example.demo.Game.Game;

public class King extends Piece {
	private boolean hasMoved;
	private Rook rook = null;
	
	public King (int x, int y, boolean isWhite, Board board, int value) {
		super();
		hasMoved = false;
	}
	
	public void initializeSide(int value) {
		super.initializeSide(value);
	}
	
	@Override
	public boolean canMove(int x, int y, Board board) {
		int i = Math.abs(xCord - x);
		int j = Math.abs(yCord - y);
		
		if(j == 1 && i == 1 || (i + j) == 1) {
			if(board.getPiece(x, y) == null) {
				return true;
			}
			else {
				return board.getPiece(x, y).isWhite() != isWhite();
			}
		}
		
		getRook(x, board);
		
		if(rook != null && (rook.HasMoved() || this.hasMoved)) {
			return false;
		}
		else if(rook != null){
			
			for(int k = xCord + 1; k < rook.getxCord(); k++) {
				if(board.getPiece(k, yCord) != null) {
					return false;
				}
				for(Movement m: Game.allEnemyMoves) {
					if((m.getToX() == k || m.getToX() == xCord) && m.getToY() == yCord) {
						return false;
					}
				}
			}	
			if(x == rook.getxCord() - 1 && y == yCord) {
				return true;
			}
			
			for(int k = xCord - 1; k > rook.getxCord(); k--) {
				if(board.getPiece(k, yCord) != null) {
					return false;
				}
				for(Movement m: Game.allEnemyMoves) {
					if((m.getToX() == k || m.getToX() == xCord) && m.getToY() == yCord) {
						return false;
					}
				}
			}
			if(x == rook.getxCord() + 2 && y == yCord) {
				return true;
			}
			
		}
		return false;
	}
	
	public boolean makeMove(int x, int y, Board board) {
		Movement move = new Movement(xCord, yCord, x, y, this);
		if(!alive()) {
			return false;
		}
		for(Movement m : moves) {
			if(m.compareTo(move) == 0) {
				getRook(x, board);
				board.updatePieces(xCord, yCord, x, y, this);
				xCord = x;
				yCord = y;
				if(rook != null && !this.hasMoved && !rook.HasMoved()) {
					if(x == rook.getxCord() - 1 || x == rook.getxCord() + 2) {
						rook.castleDone(xCord, board);
					}
				}
				hasMoved = true;
				return true;
			}
		}
		return false;
	}
	
	private void getRook(int x, Board board) {
		if(isWhite()) {
			if(x >= xCord) {
				if(board.getPiece(7, 7) != null && board.getPiece(7, 7) instanceof Rook){
					rook = (Rook) board.getPiece(7, 7);
				}
			}
			else{
				if(board.getPiece(0, 7) != null && board.getPiece(0, 7) instanceof Rook) {
					rook = (Rook) board.getPiece(0, 7);
				}
			}
		}
		else {
			if(x >= xCord) {
				if(board.getPiece(7, 0) != null && board.getPiece(7, 0) instanceof Rook) {
					rook = (Rook) board.getPiece(7, 0);					
				}
			}
			else{
				if(board.getPiece(0,0) != null && board.getPiece(0,0) instanceof Rook) {
					rook = (Rook) board.getPiece(0, 0);						
				}
			}
		}
	}
	
	public boolean isInCheck() {
		for(Movement m : Game.allEnemyMoves) {
			if(m.getToX() == xCord && m.getToY() == yCord) {
				return true;
			}
		}
		return false;
	}
}
