package com.example.demo.Pieces;

import com.example.demo.Board.Board;
import com.example.demo.Board.Movement;
import com.example.demo.Game.Game;

public class Pawn extends Piece {
	private boolean firstMove;
	private boolean legalfirstMove = false;
	
	public Pawn(int x, int y, boolean isWhite, Board board, int value) {
		super();
		firstMove = true;
	}
	
	public void initializeSide(int value) {
		super.initializeSide(value);
	}
	
	@Override
	public boolean makeMove(int toX, int toY, Board board) {
		Movement move = new Movement(xCord, yCord, toX, toY, this);
		if(!alive()) {
			return false;
		}
		if(moves.contains(move)) {
			if(toX == xCord + 1 && yCord - (isWhite ? 1 : -1) == toY) {
				if(board.getXY(toX, toY) == 0) {
					Game.allPiece.remove(board.getPiece(xCord + 1, yCord));
					Game.fillPieces();
					board.setXY(xCord + 1, yCord, 0);
					board.setPieceIntoBoard(xCord + 1, yCord, null);
				}
			}
			
			if(toX == xCord - 1 && yCord - (isWhite ? 1 : -1) == toY) {
				if(board.getXY(toX, toY) == 0) {
					Game.allPiece.remove(board.getPiece(xCord - 1, yCord));
					Game.fillPieces();
					board.setXY(xCord - 1, yCord, 0);
					board.setPieceIntoBoard(xCord - 1, yCord, null);
				}
			}
			
			if(firstMove && Math.abs((yCord - toY)) == 2) {
				legalfirstMove = true;
			}
			removeEnpassant();
			board.updatePieces(xCord, yCord, toX, toY, this);
			xCord = toX;
			yCord = toY;
			firstMove = false;
			return true;
		}
		return false;
	}
	
	private void removeEnpassant() {
		for(Piece p : Game.allPiece) {
			if(p instanceof Pawn && p != this) {
				((Pawn) p).setLegalfirstMove(false);
			}
		}
	}
	
	public boolean madeToEnd() {
		if(isWhite && yCord == 0) {
			return true;
		}
		if(!isWhite && yCord == 7) {
			return true;
		}
		return false;
	}

	public boolean canMove(int x, int y, Board board) {
		int enpassant = 0;
		
		if(isWhite) {
			enpassant = -1;
		}
		else {
			enpassant = 1;
		}
		
		if(xCord > 0 && xCord < 7) {
			if(board.getXY(xCord + 1, yCord) == enpassant) {
				Pawn leftPawn = (Pawn) board.getPiece(xCord + 1, yCord);
				if(x == leftPawn.getxCord() && y == leftPawn.getyCord() + enpassant && leftPawn.getLegalfirstMove()) {
					return true;
				}
			}
			if(board.getXY(xCord - 1, yCord) == enpassant) {
				Pawn rightPawn = (Pawn) board.getPiece(xCord + 1, yCord);
				if(x == rightPawn.getxCord() && y == rightPawn.getyCord() + enpassant && rightPawn.getLegalfirstMove()) {
					return true;
				}
			}
		}
		
		if((board.getPiece(x, y) != null && board.getPiece(x, y).isWhite() == isWhite())) {
			return false;
		}
		
		if(xCord != x && board.getPiece(x, y) == null) {
			return false;
		}
		
		if(isWhite) {
			if(firstMove) {
				if(x == xCord && (y == yCord - 1 || y == yCord - 2) && board.getPiece(x, y + 1) == null) {
					return true;
				}
			}
			
			if(x == xCord && y == yCord + 1 && board.getPiece(x, y) == null) {
				return true;
			}
			
			return capture(x, y, board);
		}
		return false;
	}
	
	public boolean capture(int x, int y, Board board) {
		if(isWhite()) {
			if(y == yCord - 1 && x == xCord + 1) {
				return true;
			}
			if(y == yCord - 1 && x == xCord - 1) {
				return true;
			}
		}
		else {
			if(y == yCord + 1 && x == xCord + 1) {
				return true;
			}
			if(y == yCord + 1 && x == xCord - 1) {
				return true;
			}
		}
		return false;
	}
	
	public void removeEnpassantCapturedpiece(int x, int y) {
		
	}
	
	public boolean getFirstMove() {
		return firstMove;
	}
	
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
	
	public boolean getLegalfirstMove() {
		return legalfirstMove;
	}
	
	public void setLegalfirstMove(boolean legalfirstMove) {
		this.legalfirstMove = legalfirstMove;
	}
}
