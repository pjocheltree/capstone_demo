package com.example.demo.Player;

//import Pieces.Bishop;
//import Pieces.Pawn;
//import Pieces.Piece;
//import Pieces.Rook;


public class Player {
	private String PlayerName;
	private int KillCount = 0;
	
	public Player(String PlayerName) {
		this.PlayerName = PlayerName;
	}
	
	
	public String getName() {
		return this.PlayerName;
	}
	
	public int getKill() {
		return this.KillCount;
	}
	
	public void addKill() {
		this.KillCount += 1;
	}
	
	public void subKill() {
		if(this.KillCount > 0) {
			this.KillCount -= 1;
		}
	}
}
