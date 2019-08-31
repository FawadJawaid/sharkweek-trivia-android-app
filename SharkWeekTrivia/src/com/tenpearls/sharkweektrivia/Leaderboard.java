package com.tenpearls.sharkweektrivia;

public class Leaderboard {

	private String name;
	private int pic;
	private int score;
	private int number;

	public Leaderboard(String sname, int spic, int score, int num){
		this.name = sname;
		this.pic = spic;
		this.score = score;
		this.number=num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPicture() {
		return pic;
	}

	public void setPicture(int img) {
		this.pic = img;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int num) {
		this.number = num;
	}


}
