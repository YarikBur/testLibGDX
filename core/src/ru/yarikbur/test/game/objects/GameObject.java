package ru.yarikbur.test.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import ru.yarikbur.test.utils.graphic.TilesetParser;

/**
 * Basic parameters for all types of objects in the game
 */
public class GameObject {
	public static final int[] SIZE_16x16 = new int[] {16, 16};
	
	protected Body body;
	protected Texture texture;
	protected TextureRegion[] textureRegion;
	protected int texture_number;
	protected int texture_column;
	protected int texture_row;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	/**
	 * Sets the texture for a given object
	 * @param path
	 */
	protected void setTexture(String path) {
		this.texture = new Texture(Gdx.files.internal(path));
	}
	
	/**
	 * Sets the texture region for a given object
	 */
	protected void setTextureRegion() {
		this.textureRegion = TilesetParser.getTileset1D(texture, texture_column, texture_row);
	}
	
	/**
	 * Sets the region ID from textures for a given object
	 * @param num - ID (int)
	 */
	protected void setTextureNumber(int num) {
		this.texture_number = num;
	}
	
	/**
	 * Sets the number of columns of regions in the texture
	 * @param value - Texture Column (int)
	 */
	protected void setTextureColumn(int value) {
		this.texture_column = value;
	}
	
	/**
	 * Sets the number of row of regions in the texture
	 * @param value - Texture row (int)
	 */
	protected void setTextureRow(int value) {
		this.texture_row = value;
	}
	
	/**
	 * Sets the X position for a given object
	 * @param value - Position x (int)
	 */
	protected void setX(int value) {
		this.x = value;
	}
	
	/**
	 * Sets the Y position for a given object
	 * @param value - Position Y (int)
	 */
	protected void setY(int value) {
		this.y = value;
	}
	
	/**
	 * Sets position for a given object
	 * @param x - Position x (int)
	 * @param y - Position Y (int)
	 */
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
		
		this.body.setPosition(getPosition());
	}
	
	/**
	 * Returns the position of this object
	 * @return Integer[x, y]
	 */
	public int[] getPosition() {
		return new int[] {x, y};
	}
	
	/**
	 * Sets the width for a given object
	 * @param width - Object width is pixels
	 */
	protected void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Sets the height for a given object
	 * @param height - Object height is pixels
	 */
	protected void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Sets the size for a given object
	 * @param width - Object width is pixels
	 * @param height - Object height is pixels
	 */
	protected void setSize(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
	}
	
	/**
	 * Sets the size for a given object
	 * @param size - integer array[width, height]
	 */
	protected void setSize(int[] size) {
		this.setWidth(size[0]);
		this.setHeight(size[1]);
	}
	
	/**
	 * Returns the size of this object
	 * @return Integer[width, height]
	 */
	public int[] getSize() {
		return new int[] {width, height};
	}
	
	/**
	 * Returns the texture of this object
	 * @return Texture
	 */
	public Texture getTexture() {
		return this.texture;
	}
	
	/**
	 * Draws the given object in the window
	 * @param batch - SpriteBatch
	 */
	public void renderObject(SpriteBatch batch) {
		batch.draw(textureRegion[texture_number], x, y);
	}
	
	/**
	 * Destroys texture and fixture
	 */
	public void dispose() {
//		texture.dispose();
		this.body.dispose();
	}
	
	/**
	 * Basic physics parameters for all types of objects in the game
	 */
	public class Body {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		com.badlogic.gdx.physics.box2d.Body worldBody;
		
		/**
		 * Set the physical type of the current object
		 * @param BodyType (StaticBody, KinematicBody or DynamicBody)
		 */
		public void setType(BodyType type) {
			bodyDef.type = type;
		}
		
		/**
		 * Returns a circular object with the specified radius
		 * @param radius float - pixels
		 * @return Shape
		 */
		public CircleShape circleShape(float radius) {
			CircleShape shape = new CircleShape();
			shape.setRadius(radius);
			
			return shape;
		}
		
		/**
		 * Returns a rectangular object with the given sides
		 * @param width float - pixels
		 * @param height float - pixels
		 * @return Shape
		 */
		public PolygonShape boxShape(float width, float height) {
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(width / 2, height / 2);
			
			return shape;
		}
		
		/**
		 * Sets the specified shape to the current physical object
		 * @param shape
		 */
		public void setFixture(Shape shape) {
			fixtureDef.shape = shape;
		}
		
		/**
		 * Sets the specified parameters for the current physical object
		 * @param shape
		 * @param density
		 * @param friction
		 * @param restitution
		 */
		public void setFixture(Shape shape, float density, float friction, float restitution) {
			fixtureDef.shape = shape;
			fixtureDef.density = density;
			fixtureDef.friction = friction;
			fixtureDef.restitution = restitution;
		}
		
		/**
		 * Sets the position of a physical object in relation to its texture dimensions
		 * @param position
		 */
		public void setPosition(int[] position) {
			int x = position[0] + getSize()[0] / 2;
			int y = position[1] + getSize()[1] / 2;
			
			bodyDef.position.set(x, y);
		}
		
		/**
		 * Returns FixtureDef of the current physics object
		 * @return FixtureDef
		 */
		public FixtureDef getFixtureDef() {
			return fixtureDef;
		}
		
		/**
		 * Returns the current physics object
		 * @return BodyDef
		 */
		public BodyDef getBodyDef() {
			return bodyDef;
		}
		
		/**
		 * Destroy the current physics object
		 */
		public void dispose() {
			fixtureDef.shape.dispose();
		}
		
		/**
		 * Sets Body of the current physics object
		 * @param worldBody - Box2D Body
		 */
		public void setWorldBody(com.badlogic.gdx.physics.box2d.Body worldBody) {
			this.worldBody = worldBody;
		}
		
		/**
		 * Returns Body of the current physics object
		 * @return Box2D Body
		 */
		public com.badlogic.gdx.physics.box2d.Body getBody() {
			return worldBody;
		}
	}
	
	/**
	 * Returns the current physics object
	 * @return BodyDef
	 */
	public BodyDef getBodyDef() {
		return this.body.getBodyDef();
	}
	
	/**
	 * Returns FixtureDef of the current physics object
	 * @return FixtureDef
	 */
	public FixtureDef getFixtureDef() {
		return this.body.getFixtureDef();
	}
	
	/**
	 * Sets Body of the current physics object
	 * @param worldBody - Box2D Body
	 */
	public void setWorldBody(com.badlogic.gdx.physics.box2d.Body worldBody) {
		this.body.worldBody = worldBody;
	}
	
	/**
	 * Returns Body of the current physics object
	 * @return Box2D Body
	 */
	public com.badlogic.gdx.physics.box2d.Body getBody() {
		return this.body.worldBody;
	}
}
