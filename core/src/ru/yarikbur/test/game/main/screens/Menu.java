package ru.yarikbur.test.game.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.yarikbur.test.game.main.MainGameWrapper;
import ru.yarikbur.test.utils.database.Queries;
import ru.yarikbur.test.utils.math.Hash;

import java.util.ArrayList;

public class Menu implements Screen {

	final MainGameWrapper wrapper;

	TextField loginField;
	TextField passwordField;
	TextButton enter;
	Label label;

	Stage stage;

	public Menu(MainGameWrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void show() {
		stage = new Stage(new ScreenViewport(), wrapper.batch);
		Gdx.input.setInputProcessor(stage);

		Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		loginField = new TextField("", skin);
		loginField.setPosition(100, 150);
		loginField.setMessageText("Login");
		loginField.setFocusTraversal(true);

		passwordField = new TextField("", skin);
		passwordField.setMessageText("Password");
		passwordField.setPasswordMode(true);
		passwordField.setPasswordCharacter('*');
		passwordField.setPosition(100, 100);

		enter = new TextButton("Enter", skin);
		enter.setPosition(100, 50);
		enter.setWidth(passwordField.getWidth());
		enter.setHeight(passwordField.getHeight());
		enter.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				String login = loginField.getText();
				String pass = passwordField.getText();

				if (Queries.checkPassword(wrapper.getDatabase_Game(), login, pass)) {
					ArrayList<String> players = Queries.getPlayers(wrapper.getDatabase_Game(), login);

					wrapper.idUser = Queries.getUID(wrapper.getDatabase_Game(), Queries.GET_USER_ID, login);
					wrapper.idPlayer = Queries.getUID(wrapper.getDatabase_Game(), Queries.GET_PLAYER_ID, players.get(0));


					GameScreen gameScreen = new GameScreen(wrapper);
					//gameScreen.setUserData(login, );

					wrapper.setScreen(gameScreen);
				} else {
					label.setText("There is an error in your password or login.");
				}

				super.touchUp(event, x, y, pointer, button);
			}
		});

		label = new Label("", skin);
		label.setPosition(100, 20);

		stage.addActor(loginField);
		stage.addActor(passwordField);
		stage.addActor(enter);
		stage.addActor(label);
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
