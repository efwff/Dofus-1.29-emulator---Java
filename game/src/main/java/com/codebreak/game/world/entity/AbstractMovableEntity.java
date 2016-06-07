package com.codebreak.game.world.entity;

public abstract class AbstractMovableEntity extends AbstractEntity implements MovableObject {

	public AbstractMovableEntity(final long id) {
		super(id);
	}

	public abstract int cellId();
	public abstract int mapId();
}
