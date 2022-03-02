package com.example.demo.Board;

import com.example.demo.Pieces.Piece;

public class Movement implements Comparable<Movement>{

	int fromX, fromY, toX, toY;
	Piece piece;
	
	public Movement(int fromX, int fromY, int toX, int toY, Piece piece) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.piece = piece;
	}
	
	public int getFromX() {
		return fromX;
	}
	
	public void setFromX(int fromX) {
		this.fromX = fromX;
	}
	
	public int getFromY() {
		return fromY;
	}
	
	public void setFromY(int fromY) {
		this.fromY = fromY;
	}
	
	public int getToX() {
		return toX;
	}
	
	public void setToX(int toX) {
		this.toX = toX;
	}
	
	public int getToY() {
		return toY;
	}
	
	public void setToY(int toY) {
		this.toY = toY;
	}

	@Override
	public int compareTo(Movement o) {
		if(toX == o.getToX() && toY == o.getToY()) {
			return 0;
		}
		return -1;
	}
	
	public boolean equals(Object o) {
		Movement otherMove = (Movement) o;
		if(this.getToX() == otherMove.getToX() && this.getToY() == otherMove.getToY() 
				&& this.getFromX() == otherMove.getFromX() && this.getFromY() == otherMove.getFromY()) {
			return true;
		}
		return false;
	}

}
