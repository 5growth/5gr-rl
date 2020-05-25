package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.Filter;
import io.swagger.model.VirtualNetworkResourceInformation;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public abstract class InformationApiService {
    
    public abstract Response queryNetworkInformation(Filter informationQueryFilter,SecurityContext securityContext) throws NotFoundException;
    }
