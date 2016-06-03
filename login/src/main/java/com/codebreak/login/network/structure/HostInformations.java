package com.codebreak.login.network.structure;

public interface HostInformations {
	int gameServerId();
	int gameServerState();
	int completionState();
	boolean selectable();
	int characterCount(long accountId);
}
