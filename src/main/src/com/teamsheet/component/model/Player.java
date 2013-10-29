package com.teamsheet.component.model;

import android.graphics.Point;

public class Player {
	
	private String name;
	private int number;
	private int positionX;
	private int positionY;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	public Point getPoint() {
		return new Point(positionX, positionY);
	}
	
	
}
