package io.swagger.api.factories;

import io.swagger.api.ComputeResourcesApiService;
import io.swagger.api.impl.ComputeResourcesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public class ComputeResourcesApiServiceFactory {
    private final static ComputeResourcesApiService service = new ComputeResourcesApiServiceImpl();

    public static ComputeResourcesApiService getComputeResourcesApi() {
        return service;
    }
}
