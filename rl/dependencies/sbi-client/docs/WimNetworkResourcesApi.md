# WimNetworkResourcesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**allocateNetwork**](WimNetworkResourcesApi.md#allocateNetwork) | **POST** /network-resources | 
[**collectWimAbstractedInformation**](WimNetworkResourcesApi.md#collectWimAbstractedInformation) | **GET** /abstract-network | Retrieve aggregated WAN Connectivity
[**queryNetworks**](WimNetworkResourcesApi.md#queryNetworks) | **GET** /network-resources | 
[**terminateNetworks**](WimNetworkResourcesApi.md#terminateNetworks) | **DELETE** /network-resources/{networkId} | 


<a name="allocateNetwork"></a>
# **allocateNetwork**
> AllocateReply allocateNetwork(params)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.WimNetworkResourcesApi;


WimNetworkResourcesApi apiInstance = new WimNetworkResourcesApi();
AllocateParameters params = new AllocateParameters(); // AllocateParameters | 
try {
    AllocateReply result = apiInstance.allocateNetwork(params);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WimNetworkResourcesApi#allocateNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **params** | [**AllocateParameters**](AllocateParameters.md)|  |

### Return type

[**AllocateReply**](AllocateReply.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="collectWimAbstractedInformation"></a>
# **collectWimAbstractedInformation**
> InlineResponse2002 collectWimAbstractedInformation()

Retrieve aggregated WAN Connectivity

Retrieve aggregated WAN Connectivity

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.WimNetworkResourcesApi;


WimNetworkResourcesApi apiInstance = new WimNetworkResourcesApi();
try {
    InlineResponse2002 result = apiInstance.collectWimAbstractedInformation();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WimNetworkResourcesApi#collectWimAbstractedInformation");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="queryNetworks"></a>
# **queryNetworks**
> List&lt;VirtualNetwork&gt; queryNetworks(networkQueryFilter)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.WimNetworkResourcesApi;


WimNetworkResourcesApi apiInstance = new WimNetworkResourcesApi();
Filter networkQueryFilter = new Filter(); // Filter | Query filter based on e.g. name, identifier, meta-data information or status information, expressing the type of information to be retrieved. It can also be used to specify one or more resources to be queried by providing their identifiers.
try {
    List<VirtualNetwork> result = apiInstance.queryNetworks(networkQueryFilter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WimNetworkResourcesApi#queryNetworks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkQueryFilter** | [**Filter**](Filter.md)| Query filter based on e.g. name, identifier, meta-data information or status information, expressing the type of information to be retrieved. It can also be used to specify one or more resources to be queried by providing their identifiers. |

### Return type

[**List&lt;VirtualNetwork&gt;**](VirtualNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="terminateNetworks"></a>
# **terminateNetworks**
> List&lt;NetworkIds&gt; terminateNetworks(networkId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.WimNetworkResourcesApi;


WimNetworkResourcesApi apiInstance = new WimNetworkResourcesApi();
String networkId = "networkId_example"; // String | Identifier of the virtualised network resource(s) to be terminated.
try {
    List<NetworkIds> result = apiInstance.terminateNetworks(networkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WimNetworkResourcesApi#terminateNetworks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkId** | **String**| Identifier of the virtualised network resource(s) to be terminated. |

### Return type

[**List&lt;NetworkIds&gt;**](NetworkIds.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

