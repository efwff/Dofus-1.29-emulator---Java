package com.codebreak.game.logic.world.impl;

import java.util.Optional;
import java.util.Set;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.logic.world.MapSource;
import com.codebreak.game.world.map.Map;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public final class Maps implements MapSource {
	
	private final Database db;
	private final TIntObjectMap<Set<Map>> maps;
	
	public Maps(final Database db) {
		this.db = db;
		this.maps = new TIntObjectHashMap<Set<Map>>();
	}

	@Override
	public Optional<Map> byId(int mapId) {
		if(maps.containsKey(mapId)) 
			return maps.get(mapId).stream().findFirst();
		return Optional.empty();
	}

}
