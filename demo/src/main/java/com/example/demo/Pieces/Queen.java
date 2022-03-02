package com.example.demo.Pieces;

import com.example.demo.Board.Board;

public class Queen extends Piece {

	public Queen(int x, int y, boolean isWhite, Board board, int value) {
		super();
	}
	
	public void initialize(int value) {
		super.initializeSide(value);
	}
	
	@Override
	public boolean canMove(int x, int y, Board board) {
		
		if(board.getPiece(x, y) != null && board.getPiece(x, y).isWhite() == isWhite()) {
			return false;
		}
		if(Math.abs(x - xCord) == Math.abs(y - yCord)) {
			return queenMovesDiagonal(x, y, board);
		}
		if(x == xCord || y == yCord) {
			return queenMovesStraight(x, y, board);
		}
		return false;
	}
	
	public boolean queenMovesStraight(int x, int y, Board board) {
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
		
		if(y == yCord && (x < xCord)) {
			for(int i = xCord - 1; i > x; i--) {
				if(board.getXY(i, y) != 0) {
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
		return false;
	}
	
	public boolean queenMovesDiagonal(int x, int y, Board board) {
		if(x > xCord && y > yCord) {
			int j = yCord + 1;
			for(int i = xCord + 1; i < x; i++) {
				if(board.getXY(i, j) != 0) {
					return false;
				}
				j++;
			}
		}
		
		else if (x < xCord && y < yCord) {
			int j = yCord - 1;
			for(int i = xCord - 1; i > x; i--) {
				if(board.getXY(i, j)!= 0) {
					return false;
				}
				j--;
			}
		}
		
		else if(x > xCord && y < yCord) {
			int j = yCord - 1;
			for(int i = xCord + 1; i < x; i++) {
				if(board.getXY(i, j) != 0) {
					return false;
				}
				j--;
			}
		}
		
		else if(x < xCord && y > yCord) {
			int j = yCord + 1;
			for(int i = xCord - 1; i > x; i--) {
				if(board.getXY(i, j) != 0) {
					return false;
				}
				j++;
			}
		}
		return true;
	}

}
