package com.codebreak.game.logic.statistic.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.util.TypedObserver;
import com.codebreak.game.logic.account.AccountEvent;
import com.codebreak.game.logic.account.AccountSource;
import com.codebreak.game.logic.statistic.OnlinePlayersSource;

import gnu.trove.set.hash.THashSet;

public final class OnlinePlayers implements OnlinePlayersSource, TypedObserver<AccountEvent> {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OnlinePlayers.class);
	
	private final Set<Long> onlineAccounts;
	private final Set<Long> onlineCharacters;
	
	public OnlinePlayers(final AccountSource accounts) {
		this.onlineAccounts = new THashSet<>();
		this.onlineCharacters = new THashSet<>();
		accounts.addObserver(this);
	}
	
	@Override
	public Set<Long> onlineAccounts() {
		return new THashSet<>(this.onlineAccounts);
	}
	
	@Override
	public Set<Long> onlineCharacters() {
		return new THashSet<>(this.onlineCharacters);
	}
	
	@Override
	public void onEvent(AccountEvent event) {
		switch(event.type()) {
			case CONNECTED:
				this.onlineAccounts.add(event.accountId());
				break;
			case DISCONNECTED:
				this.onlineAccounts.remove(event.accountId());
				break;
		}
		LOGGER.debug("online accounts : " + this.onlineAccounts.size());
	}
}
