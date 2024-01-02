package ru.yarikbur.test.game.main.render;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.ImageResolver.AssetManagerImageResolver;
import com.badlogic.gdx.maps.ImageResolver.DirectImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.BaseTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.XmlReader.Element;

import ru.yarikbur.test.game.main.map.Maps;

public class LoaderTmx extends BaseTmxMapLoader<LoaderTmx.Parameters> {
	
	private Maps.Seasons seasons;

	public LoaderTmx () {
		super(new InternalFileHandleResolver());
	}
	
	public LoaderTmx (FileHandleResolver resolver) {
		super(resolver);
	}
	
	public TiledMap load (String fileName, Maps.Seasons seasons) {
		this.seasons = seasons;
		return load(fileName, new TmxMapLoader.Parameters());
	}
	
	public TiledMap load (String fileName, TmxMapLoader.Parameters parameter) {
		FileHandle tmxFile = resolve(fileName);

		this.root = xml.parse(tmxFile);

		ObjectMap<String, Texture> textures = new ObjectMap<String, Texture>();

		final Array<FileHandle> textureFiles = getDependencyFileHandles(tmxFile);
		for (FileHandle textureFile : textureFiles) {
			Texture texture = new Texture(textureFile, parameter.generateMipMaps);
			texture.setFilter(parameter.textureMinFilter, parameter.textureMagFilter);
			textures.put(textureFile.path(), texture);
		}

		TiledMap map = loadTiledMap(tmxFile, parameter, new DirectImageResolver(textures));
		map.setOwnedResources(textures.values().toArray());
		return map;
	}
	
	@Override
	protected TiledMap loadTiledMap (FileHandle tmxFile, BaseTmxMapLoader.Parameters parameter, ImageResolver imageResolver) {
		this.map = new TiledMap();
		this.idToObject = new IntMap<>();
		this.runOnEndOfLoadTiled = new Array<>();

		if (parameter != null) {
			this.convertObjectToTileSpace = parameter.convertObjectToTileSpace;
			this.flipY = parameter.flipY;
		} else {
			this.convertObjectToTileSpace = false;
			this.flipY = true;
		}

		String mapOrientation = root.getAttribute("orientation", null);
		int mapWidth = root.getIntAttribute("width", 0);
		int mapHeight = root.getIntAttribute("height", 0);
		int tileWidth = root.getIntAttribute("tilewidth", 0);
		int tileHeight = root.getIntAttribute("tileheight", 0);
		int hexSideLength = root.getIntAttribute("hexsidelength", 0);
		String staggerAxis = root.getAttribute("staggeraxis", null);
		String staggerIndex = root.getAttribute("staggerindex", null);
		String mapBackgroundColor = root.getAttribute("backgroundcolor", null);

		MapProperties mapProperties = map.getProperties();
		if (mapOrientation != null) {
			mapProperties.put("orientation", mapOrientation);
		}
		mapProperties.put("width", mapWidth);
		mapProperties.put("height", mapHeight);
		mapProperties.put("tilewidth", tileWidth);
		mapProperties.put("tileheight", tileHeight);
		mapProperties.put("hexsidelength", hexSideLength);
		if (staggerAxis != null) {
			mapProperties.put("staggeraxis", staggerAxis);
		}
		if (staggerIndex != null) {
			mapProperties.put("staggerindex", staggerIndex);
		}
		if (mapBackgroundColor != null) {
			mapProperties.put("backgroundcolor", mapBackgroundColor);
		}
		this.mapTileWidth = tileWidth;
		this.mapTileHeight = tileHeight;
		this.mapWidthInPixels = mapWidth * tileWidth;
		this.mapHeightInPixels = mapHeight * tileHeight;

		if (mapOrientation != null) {
			if ("staggered".equals(mapOrientation)) {
				if (mapHeight > 1) {
					this.mapWidthInPixels += tileWidth / 2;
					this.mapHeightInPixels = mapHeightInPixels / 2 + tileHeight / 2;
				}
			}
		}

		Element properties = root.getChildByName("properties");
		if (properties != null) {
			loadProperties(map.getProperties(), properties);
		}

		Array<Element> tilesets = root.getChildrenByName("tileset");
		for (Element element : tilesets) {
			loadTileSet(element, tmxFile, imageResolver);
			root.removeChild(element);
		}

		for (int i = 0, j = root.getChildCount(); i < j; i++) {
			Element element = root.getChild(i);
			loadLayer(map, map.getLayers(), element, tmxFile, imageResolver);
		}

		// update hierarchical parallax scrolling factors
		// in Tiled the final parallax scrolling factor of a layer is the multiplication of its factor with all its parents
		// 1) get top level groups
		final Array<MapGroupLayer> groups = map.getLayers().getByType(MapGroupLayer.class);
		while (groups.notEmpty()) {
			final MapGroupLayer group = groups.first();
			groups.removeIndex(0);

			for (MapLayer child : group.getLayers()) {
				child.setParallaxX(child.getParallaxX() * group.getParallaxX());
				child.setParallaxY(child.getParallaxY() * group.getParallaxY());
				if (child instanceof MapGroupLayer) {
					// 2) handle any child groups
					groups.add((MapGroupLayer)child);
				}
			}
		}

		for (Runnable runnable : runOnEndOfLoadTiled) {
			runnable.run();
		}
		runOnEndOfLoadTiled = null;

		return map;
	}
	
	protected void loadTileSet (Element element, FileHandle tmxFile, ImageResolver imageResolver) {
		if (element.getName().equals("tileset")) {
			int firstgid = element.getIntAttribute("firstgid", 1);
			String imageSource = "";
			int imageWidth = 0;
			int imageHeight = 0;
			FileHandle image = null;

			String source = element.getAttribute("source", null);
			
			for (Maps.Seasons s : Maps.Seasons.values()) {
				if (source.indexOf(s.getName()) == 0) {
					source = seasons.getPath();
					break;
				}
			}
			
			if (source != null) {
				FileHandle tsx = getRelativeFileHandle(tmxFile, source);
				try {
					element = xml.parse(tsx);
					Element imageElement = element.getChildByName("image");
					if (imageElement != null) {
						imageSource = imageElement.getAttribute("source");
						imageWidth = imageElement.getIntAttribute("width", 0);
						imageHeight = imageElement.getIntAttribute("height", 0);
						image = getRelativeFileHandle(tsx, imageSource);
					}
				} catch (SerializationException e) {
					throw new GdxRuntimeException("Error parsing external tileset.");
				}
			} else {
				Element imageElement = element.getChildByName("image");
				if (imageElement != null) {
					imageSource = imageElement.getAttribute("source");
					imageWidth = imageElement.getIntAttribute("width", 0);
					imageHeight = imageElement.getIntAttribute("height", 0);
					image = getRelativeFileHandle(tmxFile, imageSource);
				}
			}
			String name = element.get("name", null);
			int tilewidth = element.getIntAttribute("tilewidth", 0);
			int tileheight = element.getIntAttribute("tileheight", 0);
			int spacing = element.getIntAttribute("spacing", 0);
			int margin = element.getIntAttribute("margin", 0);

			Element offset = element.getChildByName("tileoffset");
			int offsetX = 0;
			int offsetY = 0;
			if (offset != null) {
				offsetX = offset.getIntAttribute("x", 0);
				offsetY = offset.getIntAttribute("y", 0);
			}
			TiledMapTileSet tileSet = new TiledMapTileSet();

			// TileSet
			tileSet.setName(name);
			final MapProperties tileSetProperties = tileSet.getProperties();
			Element properties = element.getChildByName("properties");
			if (properties != null) {
				loadProperties(tileSetProperties, properties);
			}
			tileSetProperties.put("firstgid", firstgid);

			// Tiles
			Array<Element> tileElements = element.getChildrenByName("tile");

			addStaticTiles(tmxFile, imageResolver, tileSet, element, tileElements, name, firstgid, tilewidth, tileheight, spacing,
				margin, source, offsetX, offsetY, imageSource, imageWidth, imageHeight, image);

			Array<AnimatedTiledMapTile> animatedTiles = new Array<AnimatedTiledMapTile>();

			for (Element tileElement : tileElements) {
				int localtid = tileElement.getIntAttribute("id", 0);
				TiledMapTile tile = tileSet.getTile(firstgid + localtid);
				if (tile != null) {
					AnimatedTiledMapTile animatedTile = createAnimatedTile(tileSet, tile, tileElement, firstgid);
					if (animatedTile != null) {
						animatedTiles.add(animatedTile);
						tile = animatedTile;
					}
					addTileProperties(tile, tileElement);
					addTileObjectGroup(tile, tileElement);
				}
			}

			// replace original static tiles by animated tiles
			for (AnimatedTiledMapTile animatedTile : animatedTiles) {
				tileSet.putTile(animatedTile.getId(), animatedTile);
			}

			map.getTileSets().addTileSet(tileSet);
		}
	}
	
	protected Array<FileHandle> getDependencyFileHandles (FileHandle tmxFile) {
		Array<FileHandle> fileHandles = new Array<FileHandle>();

		// TileSet descriptors
		for (Element tileset : root.getChildrenByName("tileset")) {
			String source = tileset.getAttribute("source", null);
			
			for (Maps.Seasons s : Maps.Seasons.values()) {
				if (source.indexOf(s.getName()) == 0) {
					source = seasons.getPath();
					break;
				}
			}
			
			if (source != null) {
				FileHandle tsxFile = getRelativeFileHandle(tmxFile, source);
				
				tileset = xml.parse(tsxFile);
				Element imageElement = tileset.getChildByName("image");
				if (imageElement != null) {
					String imageSource = tileset.getChildByName("image").getAttribute("source");
					FileHandle image = getRelativeFileHandle(tsxFile, imageSource);
					fileHandles.add(image);
				} else {
					for (Element tile : tileset.getChildrenByName("tile")) {
						String imageSource = tile.getChildByName("image").getAttribute("source");
						FileHandle image = getRelativeFileHandle(tsxFile, imageSource);
						fileHandles.add(image);
					}
				}
			} else {
				Element imageElement = tileset.getChildByName("image");
				if (imageElement != null) {
					String imageSource = tileset.getChildByName("image").getAttribute("source");
					FileHandle image = getRelativeFileHandle(tmxFile, imageSource);
					fileHandles.add(image);
				} else {
					for (Element tile : tileset.getChildrenByName("tile")) {
						String imageSource = tile.getChildByName("image").getAttribute("source");
						FileHandle image = getRelativeFileHandle(tmxFile, imageSource);
						fileHandles.add(image);
					}
				}
			}
		}

		// ImageLayer descriptors
		for (Element imageLayer : root.getChildrenByName("imagelayer")) {
			Element image = imageLayer.getChildByName("image");
			String source = image.getAttribute("source", null);

			if (source != null) {
				FileHandle handle = getRelativeFileHandle(tmxFile, source);
				fileHandles.add(handle);
			}
		}

		return fileHandles;
	}

	
	@Override
	protected Array<AssetDescriptor> getDependencyAssetDescriptors (FileHandle tmxFile,
			TextureLoader.TextureParameter textureParameter) {
			Array<AssetDescriptor> descriptors = new Array<AssetDescriptor>();

			final Array<FileHandle> fileHandles = getDependencyFileHandles(tmxFile);
			for (FileHandle handle : fileHandles) {
				descriptors.add(new AssetDescriptor(handle, Texture.class, textureParameter));
			}

			return descriptors;
		}

	@Override
	protected void addStaticTiles (FileHandle tmxFile, ImageResolver imageResolver, TiledMapTileSet tileSet, Element element,
			Array<Element> tileElements, String name, int firstgid, int tilewidth, int tileheight, int spacing, int margin,
			String source, int offsetX, int offsetY, String imageSource, int imageWidth, int imageHeight, FileHandle image) {

			MapProperties props = tileSet.getProperties();
			if (image != null) {
				// One image for the whole tileSet
				System.out.println("Add static tiles: " + image.path());
				TextureRegion texture = imageResolver.getImage(image.path());
				

				props.put("imagesource", imageSource);
				props.put("imagewidth", imageWidth);
				props.put("imageheight", imageHeight);
				props.put("tilewidth", tilewidth);
				props.put("tileheight", tileheight);
				props.put("margin", margin);
				props.put("spacing", spacing);

				int stopWidth = texture.getRegionWidth() - tilewidth;
				int stopHeight = texture.getRegionHeight() - tileheight;

				int id = firstgid;

				for (int y = margin; y <= stopHeight; y += tileheight + spacing) {
					for (int x = margin; x <= stopWidth; x += tilewidth + spacing) {
						TextureRegion tileRegion = new TextureRegion(texture, x, y, tilewidth, tileheight);
						int tileId = id++;
						addStaticTiledMapTile(tileSet, tileRegion, tileId, offsetX, offsetY);
					}
				}
			} else {
				// Every tile has its own image source
				for (Element tileElement : tileElements) {
					Element imageElement = tileElement.getChildByName("image");
					if (imageElement != null) {
						imageSource = imageElement.getAttribute("source");

						if (source != null) {
							image = getRelativeFileHandle(getRelativeFileHandle(tmxFile, source), imageSource);
						} else {
							image = getRelativeFileHandle(tmxFile, imageSource);
						}
					}
					TextureRegion texture = imageResolver.getImage(image.path());
					int tileId = firstgid + tileElement.getIntAttribute("id");
					addStaticTiledMapTile(tileSet, texture, tileId, offsetX, offsetY);
				}
			}
	}

	@Override
	public void loadAsync (AssetManager manager, String fileName, FileHandle tmxFile, Parameters parameter) {
		this.map = loadTiledMap(tmxFile, parameter, new AssetManagerImageResolver(manager));
	}

	@Override
	public TiledMap loadSync (AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		return map;
	}

}
