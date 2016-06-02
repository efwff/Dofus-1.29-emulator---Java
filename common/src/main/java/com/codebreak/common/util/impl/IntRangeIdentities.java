package com.codebreak.common.util.impl;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.codebreak.common.util.AbstractIdentityStack;

public final class IntRangeIdentities extends AbstractIdentityStack<Integer> {
	public IntRangeIdentities(final int minInclusive, final int maxExclusive) {
		super(IntStream
				.range(minInclusive, maxExclusive)
				.boxed()
				.collect(Collectors.toList())
		);
	}
}