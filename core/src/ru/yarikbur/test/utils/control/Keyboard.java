package ru.yarikbur.test.utils.control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import ru.yarikbur.test.game.objects.entity.Player;

public class Keyboard implements InputProcessor {
	private static final Vector2 PLAYER_X = new Vector2(Player.START_SPEED, 0);
	private static final Vector2 PLAYER_Y = new Vector2(0, Player.START_SPEED);
	private Vector2 playerVector = new Vector2();
	private int time = 0;
	
	public Vector2 getPlayerVector() {
		return playerVector;
	}
	
	public int getTime() {
		return time;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.D) Player.direction.add(PLAYER_X);
		else if (keycode == Input.Keys.A) Player.direction.sub(PLAYER_X);
		if (keycode == Input.Keys.W) Player.direction.add(PLAYER_Y);
		else if (keycode == Input.Keys.S) Player.direction.sub(PLAYER_Y);
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.D) Player.direction.sub(PLAYER_X);
		else if (keycode == Input.Keys.A) Player.direction.add(PLAYER_X);
		if (keycode == Input.Keys.W) Player.direction.sub(PLAYER_Y);
		else if (keycode == Input.Keys.S) Player.direction.add(PLAYER_Y);
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
