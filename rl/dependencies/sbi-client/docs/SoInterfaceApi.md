# SoInterfaceApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**abstractOperateCompute**](SoInterfaceApi.md#abstractOperateCompute) | **POST** /abstract_compute_operate_resources | 
[**allocateAbstractCompute**](SoInterfaceApi.md#allocateAbstractCompute) | **POST** /abstract-compute-resources | Allocate abstract compute resources
[**collectMtpCloudNetworkResourceInformation**](SoInterfaceApi.md#collectMtpCloudNetworkResourceInformation) | **GET** /abstract-resources | Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity
[**createInterNfviPoPConnectivity**](SoInterfaceApi.md#createInterNfviPoPConnectivity) | **POST** /abstract-network-resources | Create inter-NFVI-PoP connectivity
[**deleteInterNfviPoPConnectivity**](SoInterfaceApi.md#deleteInterNfviPoPConnectivity) | **DELETE** /abstract-network-resources | Delete inter-NFVI-PoP connectivity
[**terminateResources**](SoInterfaceApi.md#terminateResources) | **DELETE** /abstract-compute-resources | Terminate abstract compute resources


<a name="abstractOperateCompute"></a>
# **abstractOperateCompute**
> VirtualCompute abstractOperateCompute(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoInterfaceApi;


SoInterfaceApi apiInstance = new SoInterfaceApi();
OperateComputeRequest body = new OperateComputeRequest(); // OperateComputeRequest | 
try {
    VirtualCompute result = apiInstance.abstractOperateCompute(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoInterfaceApi#abstractOperateCompute");
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

<a name="allocateAbstractCompute"></a>
# **allocateAbstractCompute**
> VirtualCompute allocateAbstractCompute(body)

Allocate abstract compute resources

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoInterfaceApi;


SoInterfaceApi apiInstance = new SoInterfaceApi();
AllocateComputeRequest body = new AllocateComputeRequest(); // AllocateComputeRequest | 
try {
    VirtualCompute result = apiInstance.allocateAbstractCompute(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoInterfaceApi#allocateAbstractCompute");
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

<a name="collectMtpCloudNetworkResourceInformation"></a>
# **collectMtpCloudNetworkResourceInformation**
> InlineResponse2003 collectMtpCloudNetworkResourceInformation()

Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity

Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoInterfaceApi;


SoInterfaceApi apiInstance = new SoInterfaceApi();
try {
    InlineResponse2003 result = apiInstance.collectMtpCloudNetworkResourceInformation();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoInterfaceApi#collectMtpCloudNetworkResourceInformation");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**InlineResponse2003**](InlineResponse2003.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createInterNfviPoPConnectivity"></a>
# **createInterNfviPoPConnectivity**
> List&lt;Object&gt; createInterNfviPoPConnectivity(body)

Create inter-NFVI-PoP connectivity



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoInterfaceApi;


SoInterfaceApi apiInstance = new SoInterfaceApi();
InterNfviPopConnectivityRequest body = new InterNfviPopConnectivityRequest(); // InterNfviPopConnectivityRequest | Create inter-NfviPop Connectivity
try {
    List<Object> result = apiInstance.createInterNfviPoPConnectivity(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoInterfaceApi#createInterNfviPoPConnectivity");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**InterNfviPopConnectivityRequest**](InterNfviPopConnectivityRequest.md)| Create inter-NfviPop Connectivity |

### Return type

**List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteInterNfviPoPConnectivity"></a>
# **deleteInterNfviPoPConnectivity**
> deleteInterNfviPoPConnectivity(body)

Delete inter-NFVI-PoP connectivity



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoInterfaceApi;


SoInterfaceApi apiInstance = new SoInterfaceApi();
DeleteInterNfviPopConnectivityRequest body = new DeleteInterNfviPopConnectivityRequest(); // DeleteInterNfviPopConnectivityRequest | Delete inter-NfviPop Connectivity
try {
    apiInstance.deleteInterNfviPoPConnectivity(body);
} catch (ApiException e) {
    System.err.println("Exception when calling SoInterfaceApi#deleteInterNfviPoPConnectivity");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeleteInterNfviPopConnectivityRequest**](DeleteInterNfviPopConnectivityRequest.md)| Delete inter-NfviPop Connectivity |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="terminateResources"></a>
# **terminateResources**
> List&lt;String&gt; terminateResources(computeId)

Terminate abstract compute resources

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoInterfaceApi;


SoInterfaceApi apiInstance = new SoInterfaceApi();
List<String> computeId = Arrays.asList("computeId_example"); // List<String> | Identifier(s) of the virtualised compute resource(s) to be terminated.
try {
    List<String> result = apiInstance.terminateResources(computeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoInterfaceApi#terminateResources");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeId** | [**List&lt;String&gt;**](String.md)| Identifier(s) of the virtualised compute resource(s) to be terminated. |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

