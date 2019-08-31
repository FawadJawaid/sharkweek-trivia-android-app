package com.tenpearls.sharkweektrivia;

public class QuizInfo {

	private String name;
	private String id;

	public QuizInfo() {
		super();
	}

	public QuizInfo(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public String getQuizName() {
		return name;
	}

	public void setQuizName(String name) {
		this.name = name;
	}
	
	public String getQuizId() {
		return id;
	}

	public void setQuizId(String Id) {
		this.id = Id;
	}

	
}
