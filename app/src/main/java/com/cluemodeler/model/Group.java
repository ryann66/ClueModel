package com.cluemodeler.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Group {
	private static int nid = 0;

	public final int id;
	final Map<Card, Knowledge> contents = new HashMap<>(3);

	Group() {
		id = nid++;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Group group = (Group) o;
		return id == group.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
