package com.codebreak.common.persistence;

import java.util.Optional;

public interface Operation<T> {
	Optional<T> fetch() throws Exception;
}
