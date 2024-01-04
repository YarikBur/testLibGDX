package ru.yarikbur.test.game.objects.wall;

import ru.yarikbur.test.game.objects.GameObject;

public class Wall extends GameObject  {
	protected boolean isCanGoRightThrough = false;
	protected boolean isDoor = false;
	
	public void setIsDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}
	
	public boolean getIsDoor() {
		return isDoor;
	}
	
	public void setIsCanGoRightThrough(boolean isCanGoRightThrough) {
		this.isCanGoRightThrough = isCanGoRightThrough;
	}
	
	public boolean getIsCanGoRightThrough() {
		return isCanGoRightThrough;
	}
}
