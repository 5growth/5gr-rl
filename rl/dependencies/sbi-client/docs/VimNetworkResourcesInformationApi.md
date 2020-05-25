# VimNetworkResourcesInformationApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**queryNetworkInformation**](VimNetworkResourcesInformationApi.md#queryNetworkInformation) | **GET** /information | 


<a name="queryNetworkInformation"></a>
# **queryNetworkInformation**
> List&lt;VirtualNetworkResourceInformation&gt; queryNetworkInformation(informationQueryFilter)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimNetworkResourcesInformationApi;


VimNetworkResourcesInformationApi apiInstance = new VimNetworkResourcesInformationApi();
Filter informationQueryFilter = new Filter(); // Filter | Filter defining the information of consumable virtualised resources on which the query applies.
try {
    List<VirtualNetworkResourceInformation> result = apiInstance.queryNetworkInformation(informationQueryFilter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimNetworkResourcesInformationApi#queryNetworkInformation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **informationQueryFilter** | [**Filter**](Filter.md)| Filter defining the information of consumable virtualised resources on which the query applies. |

### Return type

[**List&lt;VirtualNetworkResourceInformation&gt;**](VirtualNetworkResourceInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

