package com.codebreak.login.network.ipc;

public interface GameServerInformations {
	int gameServerId();
	int gameServerState();
	int completionState();
	boolean selectable();
	int characterCount(long accountId);
}
