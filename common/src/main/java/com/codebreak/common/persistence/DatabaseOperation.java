package com.codebreak.common.persistence;

import java.util.Optional;

public interface DatabaseOperation<T> {
	Optional<T> fetch() throws Exception;
}
