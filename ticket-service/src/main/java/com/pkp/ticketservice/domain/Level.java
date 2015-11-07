package com.pkp.ticketservice.domain;

public enum Level {
	ORCHESTRA(1, "Orchestra"), MAIN(2, "Main"), BALCONY_1(3, "Balcony 1"), BALCONY_2(4, "Balcony 2");
	
	private Integer id;
	private String name;
	
	Level(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public static Level forId(Integer id) {
		Level[] values = Level.values();
		for (Level level : values) {
			if(level.id.equals(id)) return level;
		}
		return null;
	}
	
	public static Level getNextLevel(Level level, Level maxLevel) {
		if(level.getId() + 1 <= maxLevel.id) {
			return forId(level.getId() + 1);
		}
		return null;
	}

}
