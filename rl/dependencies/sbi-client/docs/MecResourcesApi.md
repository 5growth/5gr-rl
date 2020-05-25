# MecResourcesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**collectMecAbstractedInformation**](MecResourcesApi.md#collectMecAbstractedInformation) | **GET** /abstract-mec-resources | Retrieve MEC specific info for MEC PoP capable


<a name="collectMecAbstractedInformation"></a>
# **collectMecAbstractedInformation**
> InlineResponse200 collectMecAbstractedInformation()

Retrieve MEC specific info for MEC PoP capable

Retrieve MEC specific info for MEC PoP capable

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MecResourcesApi;


MecResourcesApi apiInstance = new MecResourcesApi();
try {
    InlineResponse200 result = apiInstance.collectMecAbstractedInformation();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MecResourcesApi#collectMecAbstractedInformation");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

