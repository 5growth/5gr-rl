# VimResourceQuotaApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createComputeQuota**](VimResourceQuotaApi.md#createComputeQuota) | **POST** /quotas/compute_resources | 
[**createNetworkQuota**](VimResourceQuotaApi.md#createNetworkQuota) | **POST** /quotas/network_resources | 
[**queryComputeQuota**](VimResourceQuotaApi.md#queryComputeQuota) | **GET** /quotas/compute_resources | 
[**queryNetworkQuota**](VimResourceQuotaApi.md#queryNetworkQuota) | **GET** /quotas/network_resources | 
[**terminateComputeQuota**](VimResourceQuotaApi.md#terminateComputeQuota) | **DELETE** /quotas/compute_resources | 
[**terminateNetworkQuota**](VimResourceQuotaApi.md#terminateNetworkQuota) | **DELETE** /quotas/network_resources | 


<a name="createComputeQuota"></a>
# **createComputeQuota**
> VirtualComputeQuota createComputeQuota(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceQuotaApi;


VimResourceQuotaApi apiInstance = new VimResourceQuotaApi();
CreateComputeResourceQuotaRequest body = new CreateComputeResourceQuotaRequest(); // CreateComputeResourceQuotaRequest | 
try {
    VirtualComputeQuota result = apiInstance.createComputeQuota(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceQuotaApi#createComputeQuota");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateComputeResourceQuotaRequest**](CreateComputeResourceQuotaRequest.md)|  |

### Return type

[**VirtualComputeQuota**](VirtualComputeQuota.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createNetworkQuota"></a>
# **createNetworkQuota**
> VirtualNetworkQuota createNetworkQuota(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceQuotaApi;


VimResourceQuotaApi apiInstance = new VimResourceQuotaApi();
CreateNetworkResourceQuotaRequest body = new CreateNetworkResourceQuotaRequest(); // CreateNetworkResourceQuotaRequest | 
try {
    VirtualNetworkQuota result = apiInstance.createNetworkQuota(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceQuotaApi#createNetworkQuota");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateNetworkResourceQuotaRequest**](CreateNetworkResourceQuotaRequest.md)|  |

### Return type

[**VirtualNetworkQuota**](VirtualNetworkQuota.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryComputeQuota"></a>
# **queryComputeQuota**
> List&lt;VirtualComputeQuota&gt; queryComputeQuota()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceQuotaApi;


VimResourceQuotaApi apiInstance = new VimResourceQuotaApi();
try {
    List<VirtualComputeQuota> result = apiInstance.queryComputeQuota();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceQuotaApi#queryComputeQuota");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;VirtualComputeQuota&gt;**](VirtualComputeQuota.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryNetworkQuota"></a>
# **queryNetworkQuota**
> List&lt;VirtualNetworkQuota&gt; queryNetworkQuota()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceQuotaApi;


VimResourceQuotaApi apiInstance = new VimResourceQuotaApi();
try {
    List<VirtualNetworkQuota> result = apiInstance.queryNetworkQuota();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceQuotaApi#queryNetworkQuota");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;VirtualNetworkQuota&gt;**](VirtualNetworkQuota.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="terminateComputeQuota"></a>
# **terminateComputeQuota**
> List&lt;String&gt; terminateComputeQuota(resourceGroupId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceQuotaApi;


VimResourceQuotaApi apiInstance = new VimResourceQuotaApi();
List<String> resourceGroupId = Arrays.asList("resourceGroupId_example"); // List<String> | Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
try {
    List<String> result = apiInstance.terminateComputeQuota(resourceGroupId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceQuotaApi#terminateComputeQuota");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resourceGroupId** | [**List&lt;String&gt;**](String.md)| Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="terminateNetworkQuota"></a>
# **terminateNetworkQuota**
> ResourceGroupIds terminateNetworkQuota(resourceGroupId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceQuotaApi;


VimResourceQuotaApi apiInstance = new VimResourceQuotaApi();
List<String> resourceGroupId = Arrays.asList("resourceGroupId_example"); // List<String> | Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
try {
    ResourceGroupIds result = apiInstance.terminateNetworkQuota(resourceGroupId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceQuotaApi#terminateNetworkQuota");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resourceGroupId** | [**List&lt;String&gt;**](String.md)| Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. |

### Return type

[**ResourceGroupIds**](ResourceGroupIds.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

