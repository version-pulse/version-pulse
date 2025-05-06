package io.versionpulse.util;

public enum TypeCategory {
	/** A primitive type like int, long, etc. */
	PRIMITIVE,

	/** A wrapper type like Integer, Long, etc. */
	WRAPPER,

	/** A user-defined class (not built-in Java class). */
	USER_DEFINED,

	/** A List type (e.g., List<T>) */
	LIST,

	/** A Map type (e.g., Map<K, V>) */
	MAP
}
