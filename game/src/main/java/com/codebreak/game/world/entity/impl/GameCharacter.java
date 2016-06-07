package com.codebreak.game.world.entity.impl;

import com.codebreak.game.persistence.tables.records.PlayerRecord;
import com.codebreak.game.world.entity.AbstractCombatEntity;

public final class GameCharacter extends AbstractCombatEntity {
	
	private static final String LOOK_EMPTY = ",,,,,";
	
	private final PlayerRecord record;
	
	public GameCharacter(final PlayerRecord record) {
		super(record.getId(), record.getName());
		this.record = record;
	}
	
	@Override
	public int cellId() {
		return this.record.getCellid();
	}
	
	@Override
	public int mapId() {
		return this.record.getMapid();
	}
	
	@Override
	public int level() {
		return this.record.getLevel();
	}
	
	@Override
	public int skin() {
		return this.record.getSkin();
	}
	
	public boolean merchant() {
		return this.record.getMerchantmode();
	}
	
	public boolean dead() {
		return this.record.getDead() == 1;
	}
	
	public int deathCount() {
		return this.record.getDeathcount();
	}
	
	public int maxLevel() {
		return this.record.getMaxlevel();
	}
	
	public String hexColors() {
		return String.format(
					"%s;%s;%s",
					Integer.toHexString(this.record.getColor1()),
					Integer.toHexString(this.record.getColor2()),
					Integer.toHexString(this.record.getColor3())
				);
	}
	
	@Override
	public void serializeAsGameMapInformations(StringBuilder message) {
		
	}
	
	public void serializeAsEquippedItemsInformations(final StringBuilder message) {
		message.append(LOOK_EMPTY);
	}
}
