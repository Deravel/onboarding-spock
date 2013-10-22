package com.example;

import java.util.ArrayList;
import java.util.List;

public class Contrived {

	private ToStringService toStringService;

	public Contrived(ToStringService toStringService) {
		this.toStringService = toStringService;
	}

	public String toString(Object object) {
		return toStringService.toString(object);
	}

	public List<String> toString(Object ... objects) {
		List<String> stringList = new ArrayList<>();

		for (Object object : objects) {
			stringList.add(toStringService.toString(object));
		}
		return stringList;
	}

	public String concatenate(Object ... objects) {
		return toStringService.concatenate(objects);
	}

}
