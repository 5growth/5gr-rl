# VimNetworkResourcesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**queryNFVIPoPNetworkInformation**](VimNetworkResourcesApi.md#queryNFVIPoPNetworkInformation) | **GET** /network_resources/nfvi-pop-network-information | 
[**queryNetworkCapacity**](VimNetworkResourcesApi.md#queryNetworkCapacity) | **GET** /network_resources/capacities | 
[**vIMallocateNetwork**](VimNetworkResourcesApi.md#vIMallocateNetwork) | **POST** /network_resources | 
[**vIMqueryNetworks**](VimNetworkResourcesApi.md#vIMqueryNetworks) | **GET** /network_resources | 
[**vIMterminateNetworks**](VimNetworkResourcesApi.md#vIMterminateNetworks) | **DELETE** /network_resources | 


<a name="queryNFVIPoPNetworkInformation"></a>
# **queryNFVIPoPNetworkInformation**
> List&lt;NfviPop&gt; queryNFVIPoPNetworkInformation(nfviPopNetworkInformationRequest)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimNetworkResourcesApi;


VimNetworkResourcesApi apiInstance = new VimNetworkResourcesApi();
Filter nfviPopNetworkInformationRequest = new Filter(); // Filter | Input filter for selecting information to query.
try {
    List<NfviPop> result = apiInstance.queryNFVIPoPNetworkInformation(nfviPopNetworkInformationRequest);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimNetworkResourcesApi#queryNFVIPoPNetworkInformation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **nfviPopNetworkInformationRequest** | [**Filter**](Filter.md)| Input filter for selecting information to query. |

### Return type

[**List&lt;NfviPop&gt;**](NfviPop.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryNetworkCapacity"></a>
# **queryNetworkCapacity**
> CapacityInformation queryNetworkCapacity(queryNetworkCapacityRequest)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimNetworkResourcesApi;


VimNetworkResourcesApi apiInstance = new VimNetworkResourcesApi();
QueryNetworkCapacityRequest queryNetworkCapacityRequest = new QueryNetworkCapacityRequest(); // QueryNetworkCapacityRequest | 
try {
    CapacityInformation result = apiInstance.queryNetworkCapacity(queryNetworkCapacityRequest);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimNetworkResourcesApi#queryNetworkCapacity");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **queryNetworkCapacityRequest** | [**QueryNetworkCapacityRequest**](QueryNetworkCapacityRequest.md)|  |

### Return type

[**CapacityInformation**](CapacityInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="vIMallocateNetwork"></a>
# **vIMallocateNetwork**
> AllocateNetworkResult vIMallocateNetwork(params)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimNetworkResourcesApi;


VimNetworkResourcesApi apiInstance = new VimNetworkResourcesApi();
AllocateNetworkRequest params = new AllocateNetworkRequest(); // AllocateNetworkRequest | 
try {
    AllocateNetworkResult result = apiInstance.vIMallocateNetwork(params);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimNetworkResourcesApi#vIMallocateNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **params** | [**AllocateNetworkRequest**](AllocateNetworkRequest.md)|  |

### Return type

[**AllocateNetworkResult**](AllocateNetworkResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="vIMqueryNetworks"></a>
# **vIMqueryNetworks**
> List&lt;VirtualNetwork&gt; vIMqueryNetworks(networkQueryFilter)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimNetworkResourcesApi;


VimNetworkResourcesApi apiInstance = new VimNetworkResourcesApi();
Filter networkQueryFilter = new Filter(); // Filter | Query filter based on e.g. name, identifier, meta-data information or status information, expressing the type of information to be retrieved. It can also be used to specify one or more resources to be queried by providing their identifiers.
try {
    List<VirtualNetwork> result = apiInstance.vIMqueryNetworks(networkQueryFilter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimNetworkResourcesApi#vIMqueryNetworks");
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

<a name="vIMterminateNetworks"></a>
# **vIMterminateNetworks**
> List&lt;String&gt; vIMterminateNetworks(networkResourceId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimNetworkResourcesApi;


VimNetworkResourcesApi apiInstance = new VimNetworkResourcesApi();
List<String> networkResourceId = Arrays.asList("networkResourceId_example"); // List<String> | Identifier of the virtualised network resource(s) to be terminated.
try {
    List<String> result = apiInstance.vIMterminateNetworks(networkResourceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimNetworkResourcesApi#vIMterminateNetworks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **networkResourceId** | [**List&lt;String&gt;**](String.md)| Identifier of the virtualised network resource(s) to be terminated. |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

