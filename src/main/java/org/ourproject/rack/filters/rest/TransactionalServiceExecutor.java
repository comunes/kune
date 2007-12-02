package org.ourproject.rack.filters.rest;

import com.google.inject.Inject;
import com.wideplay.warp.persist.Transactional;

public class TransactionalServiceExecutor {
	private final RESTMethodFinder methodFinder;
	private final RESTSerializer serializer;

	@Inject
	public TransactionalServiceExecutor(RESTMethodFinder methodFinder, RESTSerializer serializer) {
		this.methodFinder = methodFinder;
		this.serializer = serializer;
	}

	@Transactional
	public String doService(Class<?> serviceClass, String methodName, ParametersAdapter parameters, Object serviceInstance) {
		String output = null;
		RESTMethod rest = methodFinder.findMethod(methodName, parameters, serviceClass);
		if (rest != null && rest.invoke(serviceInstance)) {
			output = serializer.serialize(rest.getResponse(), rest.getFormat());
		}
		return output;
	}
}
