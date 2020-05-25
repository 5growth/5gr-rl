package io.swagger.api.factories;

import io.swagger.api.InformationApiService;
import io.swagger.api.impl.InformationApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public class InformationApiServiceFactory {
    private final static InformationApiService service = new InformationApiServiceImpl();

    public static InformationApiService getInformationApi() {
        return service;
    }
}
