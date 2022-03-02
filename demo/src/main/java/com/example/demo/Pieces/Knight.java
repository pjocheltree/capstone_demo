package com.example.demo.Pieces;

import com.example.demo.Board.Board;

public class Knight extends Piece {
	public Knight(int x, int y, boolean isWhite, Board board, int value) {
		super();
	}
	
	public void initializeSide(int value) {
		super.initializeSide(value);
	}

	@Override
	public boolean canMove(int x, int y, Board board) {
		if((board.getPiece(x, y) != null && board.getPiece(x, y).isWhite() == isWhite())) {
			return false;
		}
		if(x == xCord + 1 && y == yCord - 2) {
			return true;
		}
		if(x == xCord - 1 && y == yCord - 2) {
			return true;
		}
		if(x == xCord - 1 && y == yCord + 2) {
			return true;
		}
		if(x == xCord + 1 && y == yCord + 2) {
			return true;
		}
		if(x == xCord + 2 && y == yCord - 1) {
			return true;
		}
		if(x == xCord + 2 && y == yCord + 1) {
			return true;
		}
		if(x == xCord - 2 && y == yCord - 1) {
			return true;
		}
		if(x == xCord - 2 && y == yCord + 1) {
			return true;
		}
		return false;
	}
}
