package ru.yarikbur.test.game.main.map;

public enum Maps {
	TestMap("map/test_map.tmx", Seasons.Summer);
	
	private final String path;
	private Seasons seasons;
	
	Maps(String path, Seasons seasons) {
		this.path = path;
		this.seasons = seasons;
	}
	
	public String getPath() {
		return this.path;
	}

	public Seasons getSeasons() {
		return this.seasons;
	}
	
	public void setSeasons(Seasons seasons) {
		this.seasons = seasons;
	}
	
	public enum Seasons {
		Winter("Winter", "Winter/Winter.tsx"),
		Spring("Spring", "Spring/Spring.tsx"),
		Summer("Summer", "Summer/Summer.tsx"),
		Autumn("Autumn", "Autumn/Autumn.tsx");
		
		private String path;
		private String name;
		
		Seasons(String name, String path) {
			this.name = name;
			this.path = path;
		}
		
		public String getPath() {
			return this.path;
		}
		
		public String getName() {
			return this.name;
		}
	}
}
