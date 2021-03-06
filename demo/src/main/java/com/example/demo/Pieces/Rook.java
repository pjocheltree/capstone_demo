package com.example.demo.Pieces;

import com.example.demo.Board.Board;

public class Rook extends Piece {
	private boolean hasMoved;
	private boolean justMoved = false;
	
	public Rook(int x, int y, boolean iswhite, Board board, int value) {
		super();
		hasMoved = false;
	}
	
	@Override
	public
	boolean makeMove(int toX, int toY, Board board) {
		if(super.makeMove(toX, toY, board)) {
			if(!hasMoved) {
				justMoved = true;
			}
			else {
				justMoved = false;
			}
			hasMoved = true;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canMove(int x, int y, Board board) {
		if(board.getPiece(x,y) != null && board.getPiece(x, y).isWhite() == isWhite()) {
			return false;
		}
		
		if(x == xCord && (y < yCord)) {
			for(int i = yCord - 1; i > y; i--) {
				if(board.getXY(x, i) != 0) {
					return false;
				}
			}
			return true;
		}
		
		if(x == xCord && (y > yCord)) {
			for(int i = yCord + 1; i < y; i++) {
				if(board.getXY(x, i) != 0) {
					return false;
				}
			}
			return true;
		}
		
		if(y == yCord && (x > xCord)) {
			for(int i = xCord + 1; i < x; i++) {
				if(board.getXY(i, y) != 0) {
					return false;
				}
			}
			return true;
		}
		
		if(y == yCord && (x < xCord)) {
			for(int i = xCord - 1; i > x; i--) {
				if(board.getXY(i, y) != 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public void castleDone(int x, Board board) {
		if(x == 6) {
			board.updatePieces(xCord, yCord, x-1, yCord, this);
			xCord = x - 1;
		}
		else {
			board.updatePieces(xCord, yCord, x+1, yCord, this);
			xCord = x + 1;
		}
		hasMoved = true;
	}
	
	public boolean HasMoved() {
		return hasMoved;
	}
	
	public void sethasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public boolean justMoved() {
		return justMoved;
	}
	
	public void setjustMoved(boolean justMoved) {
		this.justMoved = justMoved;
	}
}
