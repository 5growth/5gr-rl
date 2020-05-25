package io.swagger.api.factories;

import io.swagger.api.NetworkResourcesApiService;
import io.swagger.api.impl.NetworkResourcesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-29T11:25:16.593Z")
public class NetworkResourcesApiServiceFactory {
    private final static NetworkResourcesApiService service = new NetworkResourcesApiServiceImpl();

    public static NetworkResourcesApiService getNetworkResourcesApi() {
        return service;
    }
}
