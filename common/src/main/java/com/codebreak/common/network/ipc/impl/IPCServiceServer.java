package com.codebreak.common.network.ipc.impl;

import org.terracotta.ipceventbus.event.EventBusServer;

import com.codebreak.common.network.ipc.AbstractIPCService;

public class IPCServiceServer extends AbstractIPCService {
	public IPCServiceServer(final String host, final int port) {
		super(new EventBusServer.Builder()
				.bind(host)
				.listen(port)
				.build());
	}	
}
