package ru.yarikbur.test.game.main.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import ru.yarikbur.test.game.objects.GameObject;
import ru.yarikbur.test.game.objects.entity.Player;
import ru.yarikbur.test.utils.control.GameContactListener;

/**
 * The class responsible for setting up the Box2D world
 */
public class EngineWorld {
	private static final boolean DO_SLEEP = false;
	private static final boolean WORLD_DEBUG = true;
	
	private static World world = null;
	private static Box2DDebugRenderer debugRenderer = null;
	private static ArrayList<GameObject> worldObjects = new ArrayList<GameObject>();
	private static Player player;
	
	private ShapeRenderer shapeRenderer;
	
	/**
	 * Initialization Box2D world
	 */
	public EngineWorld() {
		if (world == null)
			world = new World(new Vector2(0, 0), DO_SLEEP);
		if (debugRenderer == null && WORLD_DEBUG)
			debugRenderer = new Box2DDebugRenderer();
		Box2D.init();
		
		shapeRenderer = new ShapeRenderer();
		world.setContactListener(new GameContactListener());
	}
	
	@FunctionalInterface
	public interface UpdateWorld {
		public void update(World world);
	}
	
	public void update(UpdateWorld world) {
		EngineWorld.world.step(1/60f, 6, 2);
		world.update(EngineWorld.world);
	}
	
	/**
	 * Renders object shapes if debug mode is enabled
	 * @param matrix4 Camera combine
	 */
	public void render(Matrix4 matrix4) {
		if (debugRenderer != null) {
//			debugRenderer.render(world, matrix4);
			
			shapeRenderer.setProjectionMatrix(matrix4);
			shapeRenderer.begin(ShapeType.Line);
			for (GameObject obj : worldObjects) {
				shapeRenderer.setColor(obj.getColor());
				shapeRenderer.rect(obj.getPosition()[0], obj.getPosition()[1], obj.getSize()[0], obj.getSize()[1]);
			}
			
			shapeRenderer.setColor(player.getColor());
			shapeRenderer.rect(player.getPosition()[0]+8, player.getPosition()[1], player.getSize()[0], player.getSize()[1]);
			
			shapeRenderer.end();
		}
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
	
	public void setPlayer(Player player) {
		EngineWorld.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void addObject(GameObject gameObject, int x, int y) {
		gameObject.setPosition(x * gameObject.getSize()[0], y * gameObject.getSize()[1]);
		
		Body body = world.createBody(gameObject.getBodyDef());
		body.createFixture(gameObject.getFixtureDef());
		gameObject.setWorldBody(body);
		
		gameObject.setUserData(0, gameObject);
		
		worldObjects.add(gameObject);
	}
	
	public void destroyObject(GameObject gameObject) {
		world.destroyBody(gameObject.getBody());
		worldObjects.remove(gameObject);
	}
	
	public void clearAllObjects() {
		worldObjects = new ArrayList<GameObject>();
	}
}
