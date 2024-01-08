package ru.yarikbur.test.utils.control;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class GameContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		WorldManifold manifold = contact.getWorldManifold();
		
		for (int i = 0; i < manifold.getNumberOfContactPoints(); i++) {
//			Example contact
//			if (contact.getFixtureA().getUserData() != null && contact.getFixtureB().getUserData() != null) {
//				if (contact.getFixtureB().getUserData().getClass().equals(LayerFloor.class) && contact.getFixtureA().getUserData().getClass().equals(Player.class) ||
//						contact.getFixtureA().getUserData().getClass().equals(LayerFloor.class) && contact.getFixtureB().getUserData().getClass().equals(Player.class)) {
//					contact.setEnabled(false);
//				}
//			}
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
