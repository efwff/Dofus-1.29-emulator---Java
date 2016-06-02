package com.codebreak.login.network.structure;

public interface HostInformations extends HostCharactersInformations {
	
	public static final HostInformations FAKE_LOCAL = new HostInformations() {		
		@Override
		public boolean selectable() {
			return true;
		}		
		@Override
		public int gameServerState() {
			return 1;
		}
		
		@Override
		public int gameServerId() {
			return 1;
		}
		
		@Override
		public int completionState() {
			return 0;
		}
		@Override
		public int characterCount(long accountId) {
			return 5;
		}
	};
	
	int gameServerId();
	int gameServerState();
	int completionState();
	boolean selectable();
}
