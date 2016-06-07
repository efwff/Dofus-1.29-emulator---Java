package com.codebreak.game.world.entity;

public abstract class AbstractNamedEntity extends AbstractMovableEntity {
	private final String name;
	public AbstractNamedEntity(final long id, final String name) {
		super(id);
		this.name = name;
	}
	public String name() {
		return this.name;
	}
}
