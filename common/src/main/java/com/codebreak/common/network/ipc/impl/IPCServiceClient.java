package com.codebreak.common.network.ipc.impl;

import org.terracotta.ipceventbus.event.EventBusClient;

import com.codebreak.common.network.ipc.AbstractIPCService;

public class IPCServiceClient extends AbstractIPCService {
	public IPCServiceClient(final String host, final int port) {
		super(new EventBusClient.Builder()
				.connect(host, port)
				.build());
	}
}
