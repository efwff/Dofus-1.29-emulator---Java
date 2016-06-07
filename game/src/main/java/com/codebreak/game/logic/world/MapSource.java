package com.codebreak.game.logic.world;

import java.util.Optional;

import com.codebreak.game.world.map.Map;

public interface MapSource {
	Optional<Map> byId(final int mapId);
}
