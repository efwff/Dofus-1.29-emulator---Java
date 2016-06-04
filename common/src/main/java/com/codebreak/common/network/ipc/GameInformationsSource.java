package com.codebreak.common.network.ipc;

import com.codebreak.common.network.ipc.message.impl.GameInformations;

public interface GameInformationsSource {
	GameInformations gameInfos();
}
