# VimComputeResourcesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**allocateCompute**](VimComputeResourcesApi.md#allocateCompute) | **POST** /compute_resources | 
[**createFlavour**](VimComputeResourcesApi.md#createFlavour) | **POST** /compute_resources/flavours | 
[**deleteFlavours**](VimComputeResourcesApi.md#deleteFlavours) | **DELETE** /compute_resources/flavours/{id} | 
[**operateCompute**](VimComputeResourcesApi.md#operateCompute) | **POST** /compute_operate_resources | 
[**queryComputeCapacity**](VimComputeResourcesApi.md#queryComputeCapacity) | **GET** /compute_resources/capacities | 
[**queryComputeInformation**](VimComputeResourcesApi.md#queryComputeInformation) | **GET** /compute_resources/information | 
[**queryComputeResourceZone**](VimComputeResourcesApi.md#queryComputeResourceZone) | **GET** /compute_resources/resource_zones | 
[**queryFlavors**](VimComputeResourcesApi.md#queryFlavors) | **GET** /compute_resources/flavours | 
[**queryNFVIPoPComputeInformation**](VimComputeResourcesApi.md#queryNFVIPoPComputeInformation) | **GET** /compute_resources/nfvi_pop_compute_information | 
[**queryResources**](VimComputeResourcesApi.md#queryResources) | **GET** /compute_resources | 
[**terminateAbstractResources**](VimComputeResourcesApi.md#terminateAbstractResources) | **DELETE** /compute_resources | 


<a name="allocateCompute"></a>
# **allocateCompute**
> VirtualCompute allocateCompute(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
AllocateComputeRequest body = new AllocateComputeRequest(); // AllocateComputeRequest | 
try {
    VirtualCompute result = apiInstance.allocateCompute(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#allocateCompute");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AllocateComputeRequest**](AllocateComputeRequest.md)|  |

### Return type

[**VirtualCompute**](VirtualCompute.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createFlavour"></a>
# **createFlavour**
> String createFlavour(flavour)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
VirtualComputeFlavour flavour = new VirtualComputeFlavour(); // VirtualComputeFlavour | 
try {
    String result = apiInstance.createFlavour(flavour);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#createFlavour");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **flavour** | [**VirtualComputeFlavour**](VirtualComputeFlavour.md)|  |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteFlavours"></a>
# **deleteFlavours**
> deleteFlavours(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
String id = "id_example"; // String | Identifier of the Compute Flavour to be deleted.
try {
    apiInstance.deleteFlavours(id);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#deleteFlavours");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| Identifier of the Compute Flavour to be deleted. |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="operateCompute"></a>
# **operateCompute**
> VirtualCompute operateCompute(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
OperateComputeRequest body = new OperateComputeRequest(); // OperateComputeRequest | 
try {
    VirtualCompute result = apiInstance.operateCompute(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#operateCompute");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**OperateComputeRequest**](OperateComputeRequest.md)|  |

### Return type

[**VirtualCompute**](VirtualCompute.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryComputeCapacity"></a>
# **queryComputeCapacity**
> CapacityInformation queryComputeCapacity(computeResourceTypeId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
String computeResourceTypeId = "computeResourceTypeId_example"; // String | 
try {
    CapacityInformation result = apiInstance.queryComputeCapacity(computeResourceTypeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#queryComputeCapacity");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeResourceTypeId** | **String**|  |

### Return type

[**CapacityInformation**](CapacityInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryComputeInformation"></a>
# **queryComputeInformation**
> List&lt;VirtualComputeResourceInformation&gt; queryComputeInformation(zoneId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
String zoneId = "zoneId_example"; // String | Filter defining the information of consumable virtualised resources on which the query applies.
try {
    List<VirtualComputeResourceInformation> result = apiInstance.queryComputeInformation(zoneId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#queryComputeInformation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **zoneId** | **String**| Filter defining the information of consumable virtualised resources on which the query applies. |

### Return type

[**List&lt;VirtualComputeResourceInformation&gt;**](VirtualComputeResourceInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryComputeResourceZone"></a>
# **queryComputeResourceZone**
> List&lt;ResourceZone&gt; queryComputeResourceZone()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
try {
    List<ResourceZone> result = apiInstance.queryComputeResourceZone();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#queryComputeResourceZone");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ResourceZone&gt;**](ResourceZone.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryFlavors"></a>
# **queryFlavors**
> List&lt;VirtualComputeFlavour&gt; queryFlavors()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
try {
    List<VirtualComputeFlavour> result = apiInstance.queryFlavors();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#queryFlavors");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;VirtualComputeFlavour&gt;**](VirtualComputeFlavour.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryNFVIPoPComputeInformation"></a>
# **queryNFVIPoPComputeInformation**
> List&lt;NfviPop&gt; queryNFVIPoPComputeInformation()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
try {
    List<NfviPop> result = apiInstance.queryNFVIPoPComputeInformation();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#queryNFVIPoPComputeInformation");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;NfviPop&gt;**](NfviPop.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryResources"></a>
# **queryResources**
> List&lt;VirtualCompute&gt; queryResources()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
try {
    List<VirtualCompute> result = apiInstance.queryResources();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#queryResources");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;VirtualCompute&gt;**](VirtualCompute.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="terminateAbstractResources"></a>
# **terminateAbstractResources**
> List&lt;String&gt; terminateAbstractResources(computeId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimComputeResourcesApi;


VimComputeResourcesApi apiInstance = new VimComputeResourcesApi();
List<String> computeId = Arrays.asList("computeId_example"); // List<String> | Identifier(s) of the abstract compute resource(s) to be terminated.
try {
    List<String> result = apiInstance.terminateAbstractResources(computeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimComputeResourcesApi#terminateAbstractResources");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeId** | [**List&lt;String&gt;**](String.md)| Identifier(s) of the abstract compute resource(s) to be terminated. |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

