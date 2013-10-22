package com.example;

public class Contrived {

	private ToStringService toStringService;

	public Contrived(ToStringService toStringService) {
		this.toStringService = toStringService;
	}

	public String toString(Object object) {
		return toStringService.toString(object);
	}

	public String concatenate(Object ... objects) {
		return toStringService.concatenate(objects);
	}

}
