# DefaultApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**healthzGet**](DefaultApi.md#healthzGet) | **GET** /healthz | Healthcheck


<a name="healthzGet"></a>
# **healthzGet**
> healthzGet()

Healthcheck

&lt;br/&gt;

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
try {
    apiInstance.healthzGet();
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#healthzGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

