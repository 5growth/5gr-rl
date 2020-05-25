# RadioResourcesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**collectRadioCoverageareaInformation**](RadioResourcesApi.md#collectRadioCoverageareaInformation) | **GET** /abstract-radio-coveragearea | Retrieve Radio Coverage Area info for Radio PoP capable


<a name="collectRadioCoverageareaInformation"></a>
# **collectRadioCoverageareaInformation**
> InlineResponse2001 collectRadioCoverageareaInformation()

Retrieve Radio Coverage Area info for Radio PoP capable

Retrieve Radio Coverage Area info for Radio PoP capable

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RadioResourcesApi;


RadioResourcesApi apiInstance = new RadioResourcesApi();
try {
    InlineResponse2001 result = apiInstance.collectRadioCoverageareaInformation();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RadioResourcesApi#collectRadioCoverageareaInformation");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

