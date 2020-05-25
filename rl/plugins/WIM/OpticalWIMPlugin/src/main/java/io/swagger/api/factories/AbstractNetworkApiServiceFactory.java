package io.swagger.api.factories;

import io.swagger.api.AbstractNetworkApiService;
import io.swagger.api.impl.AbstractNetworkApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-29T11:25:16.593Z")
public class AbstractNetworkApiServiceFactory {
    private final static AbstractNetworkApiService service = new AbstractNetworkApiServiceImpl();

    public static AbstractNetworkApiService getAbstractNetworkApi() {
        return service;
    }
}
