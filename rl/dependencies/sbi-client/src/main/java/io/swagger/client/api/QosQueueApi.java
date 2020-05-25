package io.swagger.client.api;

import com.google.gson.reflect.TypeToken;
import com.rl.extinterface.nbi.swagger.model.AssignQosQueueOnosRequest;
import com.rl.extinterface.nbi.swagger.model.AssignQosQueueOnosResponse;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.Pair;
import io.swagger.client.ProgressRequestBody;
import io.swagger.client.ProgressResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QosQueueApi {
    private ApiClient apiClient;

    public QosQueueApi() {
        this(Configuration.getDefaultApiClient());
    }

    public QosQueueApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }


     /**
     *
     *
     * @param body  (required)
     * @return assignQosQueue
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public AssignQosQueueOnosResponse assignQueue(AssignQosQueueOnosRequest body) throws ApiException {
        ApiResponse<AssignQosQueueOnosResponse> resp = assignQosQueueWithHttpInfo(body);
        return resp.getData();
    }

    /**
     *
     *
     * @param body  (required)
     * @return ApiResponse&lt;assignQosQueue&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<AssignQosQueueOnosResponse> assignQosQueueWithHttpInfo(AssignQosQueueOnosRequest body) throws ApiException {
        com.squareup.okhttp.Call call = assignQosQueueValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<AssignQosQueueOnosResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call assignQosQueueValidateBeforeCall(AssignQosQueueOnosRequest body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling AssignQosQueueOnosRequest(Async)");
        }


        com.squareup.okhttp.Call call = assignQosQueueCall(body, progressListener, progressRequestListener);
        return call;

    }
    /**
     * Build call for assignQosQueue
     * @param body  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call assignQosQueueCall(AssignQosQueueOnosRequest body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/onos/v1/dpe/slice";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
}
