package ru.yarikbur.test.game.main.map;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The class responsible for setting up the Box2D world
 */
public class EngineWorld {
	private static final boolean DO_SLEEP = true;
	private static final boolean WORLD_DEBUG = true;
	
	private static World world = null;
	private static Box2DDebugRenderer debugRenderer = null;
	
	/**
	 * Initialization Box2D world
	 */
	public EngineWorld() {
		if (world == null)
			world = new World(new Vector2(0, 0), DO_SLEEP);
		if (debugRenderer == null && WORLD_DEBUG)
			debugRenderer = new Box2DDebugRenderer();
		Box2D.init();
	}
	
	/**
	 * Renders object shapes if debug mode is enabled
	 * @param matrix4 Camera combine
	 */
	public void render(Matrix4 matrix4) {
		if (debugRenderer != null)
			debugRenderer.render(world, matrix4);
	}
	
	/**
	 * Returns the current world in which objects are processed in the Box2D engine
	 * @return World box2d
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Returns a debug render of the Box2D engine
	 * @return Box2DDebugRenderer
	 */
	public Box2DDebugRenderer getDebugRenderer() {
		return debugRenderer;
	}
}
