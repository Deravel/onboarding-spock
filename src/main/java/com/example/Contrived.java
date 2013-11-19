package com.example;

import java.util.ArrayList;
import java.util.List;

public class Contrived {

	private Service service;

	public Contrived(Service service) {
		this.service = service;
	}

	public String singleParamDelegate(Object object) {
		return service.singleParamMethod(object);
	}

	public List<String> singleParamDelegateForEach(Object... objects) {
		List<String> stringList = new ArrayList<>();

		for (Object object : objects) {
			stringList.add(service.singleParamMethod(object));
		}
		return stringList;
	}

	public String multiParamDelegate(Object... objects) {
		return service.multiParamMethod(objects);
	}


    public void throwException(String message, int statusCode) {
        throw new ServiceException(message, statusCode);
    }

}
