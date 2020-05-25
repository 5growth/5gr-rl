package io.swagger.api.impl;

        import com.google.gson.Gson;
        import com.google.gson.JsonObject;
        import io.swagger.api.*;
        import io.swagger.model.*;

        import io.swagger.model.InlineResponse200;

        import java.io.*;
        import java.util.List;
        import io.swagger.api.NotFoundException;

        import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

        import javax.ws.rs.core.Response;
        import javax.ws.rs.core.SecurityContext;
        import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-29T11:25:16.593Z")
public class AbstractNetworkApiServiceImpl extends AbstractNetworkApiService {
    @Override
    public Response collectWimAbstractedInformation(SecurityContext securityContext) throws NotFoundException {
        /*System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
        C:\Users\Andrea\Documents\JavaFiles\newRepo\virtualnetworkserver
        C:\Users\Andrea\Documents\JavaFiles\newRepo\virtualnetworkserver\src\main\java\io\swagger\api\constants
        */
        String filename = System.getProperty("user.dir")+"\\src\\main\\java\\io\\swagger\\api\\constants\\topo.json";
        Gson gson = new Gson();
        String content ="";
        try {
            JsonObject jsonObject = gson.fromJson(new FileReader(filename), JsonObject.class);
            content= jsonObject.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, content)).build();
    }
}
