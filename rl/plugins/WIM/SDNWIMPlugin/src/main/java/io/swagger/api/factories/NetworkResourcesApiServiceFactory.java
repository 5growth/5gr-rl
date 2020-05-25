package io.swagger.api.factories;

import io.swagger.api.NetworkResourcesApiService;
import io.swagger.api.impl.NetworkResourcesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public class NetworkResourcesApiServiceFactory {
    private final static NetworkResourcesApiService service = new NetworkResourcesApiServiceImpl();

    public static NetworkResourcesApiService getNetworkResourcesApi() {
        return service;
    }
}
