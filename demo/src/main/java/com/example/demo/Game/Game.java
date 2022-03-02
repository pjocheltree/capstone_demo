package com.example.demo.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.demo.Board.*;
import com.example.demo.Pieces.*;

import com.example.demo.Player.Player;

public class Game {

	static Board board = new Board();
	
	static King wk;
	static King bk;
	public static ArrayList<Piece> wPiece = new ArrayList<Piece>();
	public static ArrayList<Piece> bPiece = new ArrayList<Piece>();
	
	Player players;
	static boolean player = true;
	Piece active = null;
	public static ArrayList<Piece> allPiece = new ArrayList<Piece>();
	
	ArrayList<Movement> allPossibleMoves = new ArrayList<Movement>();
	
	public static List<Movement> allPlayersMove = new ArrayList<Movement>();
	public static List<Movement> allEnemyMoves = new ArrayList<Movement>();
	private static boolean gameOver = false;
	
	public void start() {
		fillPieces();
		generatePlayerTurnMoves(board);
		generateEnemysMoves(board);
		checkPlayerMoves();
	}
	
	public static void changeSide() {
		player = !player;
		generateEnemysMoves(board);
		generatePlayerTurnMoves(board);
		checkPlayerMoves();
		checkMate();
	}
	
	public static void generateEnemysMoves(Board board) {
		allEnemyMoves = new ArrayList<Movement>();
		for(Piece p : allPiece) {
			if(p.isWhite() != player) {
				p.getPseudoLegalMoves(board);
				allEnemyMoves.addAll(p.getMoves());
			}
		}
		
	}

	public static void generatePlayerTurnMoves(Board board) {
		allPlayersMove = new ArrayList<Movement>();
		for(Piece p : allPiece) {
			if (p.isWhite() == player) {
				p.getPseudoLegalMoves(board);
				allPlayersMove.addAll(p.getMoves());
			}
		}
	}
	
	public static void checkPlayerMoves() {
		List<Piece> pieces = null;
		if(player) {
			pieces = wPiece;
		}
		else {
			pieces = bPiece;
		}
		for(Piece p : pieces) {
			checkLegalMoves(p);
		}
	}
	
	private static void checkLegalMoves(Piece p) {
		List<Movement> IllegalMoves = new ArrayList<Movement>();
		Board clone = board.getNewBoard();
		Piece clonedActive = p.getClone();
//		Player players;
		
		for(Movement move : clonedActive.getMoves()) {
			clone = board.getNewBoard();
			clonedActive = p.getClone();
			
			clonedActive.makeMove(move.getToX(), move.getToY(), clone);
			
			List<Piece> enemyPieces = new ArrayList<Piece>();
			Piece king = null;
			
			if(p.isWhite()) {
				enemyPieces = bPiece;
				king = wk;
			}
			else {
				enemyPieces = wPiece;
				king = bk;
			}
			
			for (Piece enemyP : enemyPieces) {
				
				Piece clonedEnemy = enemyP.getClone();
				clonedEnemy.getPseudoLegalMoves(clone);
				
				for(Movement bMove : clonedEnemy.getMoves()) {
					if(!(clonedActive instanceof King) && bMove.getToX() == king.getxCord()
							&& bMove.getToY() == king.getyCord() 
							&& clone.getGrid()[enemyP.getxCord()][enemyP.getyCord()] == enemyP
							.getValueInBoard()) {
						IllegalMoves.add(move);
					}
					else if (clonedActive instanceof King) {
						if(bMove.getToX() == clonedActive.getxCord() && bMove.getToY() == clonedActive.getyCord()
								&& clone.getGrid()[enemyP.getxCord()][enemyP.getyCord()] == enemyP.getValueInBoard()) {
							IllegalMoves.add(move);
						}
					}
				}
			}
		}
	}

	public static void fillPieces() {
		wPiece = new ArrayList<Piece>();
		bPiece = new ArrayList<Piece>();
		for(Piece p : allPiece) {
			if(p.isWhite()) {
				wPiece.add(p);
			}
			else {
				bPiece.add(p);
			}
		}

	}
	
	public static void checkMate() {
		if (player) {
			for (Piece p : wPiece) {
				if(!p.getMoves().isEmpty()) {
					return;
				}
			}
		}
		else {
			for (Piece p : bPiece) {
				if(!p.getMoves().isEmpty()) {
					return;
				}
			}
		}
		gameOver = true;
	}
	
	public void selectPiece(int x, int y) {
		if(active == null && board.getPiece(x, y) != null && board.getPiece(x, y).isWhite() == player) {
			active = board.getPiece(x, y);
		}
	}

	public void randomPlay() {
		if(gameOver) {
			return;
		}
		if(!player) {
			Random r = new Random();
			active = bPiece.get(r.nextInt(bPiece.size()));
			while(active.getMoves().isEmpty()) {
				active = bPiece.get(r.nextInt(bPiece.size()));
			}
			Movement m = active.getMoves().get(r.nextInt(active.getMoves().size()));
			move(m.getToX(), m.getToY());
		}
	}
	
	public void move(int x, int y) {
		if(active != null) {
			if(active.makeMove(x, y, board)) {
				changeSide();
				active = null;
			}
		}
	}
	
//	public void Promotion(Piece p) {
//		if(p instanceof Pawn) {
//			if(((Pawn) p).madeToEnd()) {
//				choosePiece(p, );
//			}
//		}
//	}
	
	public void loadFenPosition(String fenString) {
		String[] parts = fenString.split(" ");
		String position = parts[0];
		int row = 0, col = 0;
		for(char c : position.toCharArray()) {
			if (c == '/') {
				row++;
				col = 0;
			}
			if(Character.isLetter(c)) {
				if(Character.isLowerCase(c)) {
					addToBoard(col, row, c, false);
				}
				else {
					addToBoard(col, row, c, true);
				}
				col++;
			}
			if(Character.isDigit(c)) {
				col += Integer.parseInt(String.valueOf(c));
			}
		}
		
		if(parts[1].equals("w")) {
			player = true;
		}
		else {
			player = false;
		}
	}
	
	public static void choosePiece(Piece p, int choice) {
		switch(choice) {
		case 0:
			allPiece.remove(p);
			p = new Queen(p.getxCord(), p.getyCord(), p.isWhite(), board, p.isWhite() ? 8 : -8);
			allPiece.add(p);
			
			break;
		case 1:
			allPiece.remove(p);
			p = new Rook(p.getxCord(), p.getyCord(), p.isWhite(), board, p.isWhite() ? 5 : -5);
			allPiece.add(p);
			
			break;
		case 2:
			allPiece.remove(p);
			p = new Knight(p.getxCord(), p.getyCord(), p.isWhite(), board, p.isWhite() ? 3 : -3);
			allPiece.add(p);
			
			break;
		case 3:
			allPiece.remove(p);
			p = new Bishop(p.getxCord(), p.getyCord(), p.isWhite(), board, p.isWhite() ? 3 : -3);
			allPiece.add(p);
			
			break;
		default:
			allPiece.remove(p);
			p = new Queen(p.getxCord(), p.getyCord(), p.isWhite(), board, p.isWhite() ? 8 : -8);
			allPiece.add(p);
			
			break;
		}
	}
	
	public void addToBoard(int x, int y, char c, boolean isWhite) {
		switch(String.valueOf(c).toUpperCase()) {
		case "R":
			allPiece.add(new Rook(x, y, isWhite, board, isWhite ? 5 : -5));
			break;
		case "N":
			allPiece.add(new Knight(x, y, isWhite, board, isWhite ? 3 : -3));
			break;
		case "B":
			allPiece.add(new Bishop(x, y, isWhite, board, isWhite ? 3 : -3));
			break;
		case "Q":
			allPiece.add(new Queen(x, y, isWhite, board, isWhite ? 8 : -8));
			break;
		case "K":
			King king = new King(x, y, isWhite, board, isWhite ? 10 : -10);
			allPiece.add(king);
			if(isWhite) {
				wk = king;
			}
			else {
				bk = king;
			}
			break;
		case "P":
			allPiece.add(new Rook(x, y, isWhite, board, isWhite ? 1 : -1));
			break;
		}
	}
}
