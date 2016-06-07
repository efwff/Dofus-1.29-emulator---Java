package com.codebreak.game.world.entity;

public abstract class AbstractEntity {
	private final long id;
	public AbstractEntity(final long id) {
		this.id = id;
	}
	public long id() {
		return this.id;
	}
	public abstract void serializeAsGameMapInformations(final StringBuilder message);
}
