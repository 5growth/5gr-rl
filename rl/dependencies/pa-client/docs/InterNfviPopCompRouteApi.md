# InterNfviPopCompRouteApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**compRouteInterNfviPop**](InterNfviPopCompRouteApi.md#compRouteInterNfviPop) | **POST** /compRoute/{interNfviConnectivityId} | Computes the interNfviPop connectivity between a pair of PEs with specific network constraints and selects specific servers/hosts within NfviPops


<a name="compRouteInterNfviPop"></a>
# **compRouteInterNfviPop**
> InlineResponse200 compRouteInterNfviPop(interNfviConnectivityId, params)

Computes the interNfviPop connectivity between a pair of PEs with specific network constraints and selects specific servers/hosts within NfviPops

Computes the interNfviPop connectivity between a pair of PEs with specific network constraints and selects specific servers/hosts within NfviPops

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.InterNfviPopCompRouteApi;


InterNfviPopCompRouteApi apiInstance = new InterNfviPopCompRouteApi();
String interNfviConnectivityId = "interNfviConnectivityId_example"; // String | Identifier of the interNfviPop connection to be computed.
CompRouteInput params = new CompRouteInput(); // CompRouteInput | 
try {
    InlineResponse200 result = apiInstance.compRouteInterNfviPop(interNfviConnectivityId, params);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InterNfviPopCompRouteApi#compRouteInterNfviPop");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **interNfviConnectivityId** | **String**| Identifier of the interNfviPop connection to be computed. |
 **params** | [**CompRouteInput**](CompRouteInput.md)|  |

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

