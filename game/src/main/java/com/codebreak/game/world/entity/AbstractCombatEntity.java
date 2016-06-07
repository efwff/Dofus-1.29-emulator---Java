package com.codebreak.game.world.entity;

public abstract class AbstractCombatEntity extends AbstractNamedEntity {
	public AbstractCombatEntity(final long id, final String name) {
		super(id, name);
	}
	public abstract int level();
	public abstract int skin();
}
