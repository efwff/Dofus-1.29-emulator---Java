package com.codebreak.common.network.message;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class AbstractDofusMessage implements Message<String> {	
	
	public static final AbstractDofusMessage batch(AbstractDofusMessage... messages) {
		return new AbstractDofusMessage() {			
			@Override
			public String internalSerialize() {
				return Arrays.asList(messages)
							 .stream()
							 .map(message -> message.serialize())
							 .collect(Collectors.joining());
			}
		};
	}
	
	public static final AbstractDofusMessage EMPTY = new AbstractDofusMessage() {		
		@Override
		public String internalSerialize() {
			return null;
		}
		@Override
		public String serialize() {
			return "";
		}
	};
	
	private static final char PROTOCOL_SUFFIX = 0x00;	
	
	private String output;
	
	public String serialize() {
		if(output == null) {
			output = internalSerialize() + PROTOCOL_SUFFIX;
		}
		return output;
	}
	
	protected abstract String internalSerialize(); 
}
