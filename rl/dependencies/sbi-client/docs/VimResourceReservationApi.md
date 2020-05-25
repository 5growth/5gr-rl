# VimResourceReservationApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createComputeReservation**](VimResourceReservationApi.md#createComputeReservation) | **POST** /reservations/compute_resources | 
[**createNetworkReservation**](VimResourceReservationApi.md#createNetworkReservation) | **POST** /reservations/network_resources | 
[**queryComputeReservation**](VimResourceReservationApi.md#queryComputeReservation) | **GET** /reservations/compute_resources | 
[**queryNetworkReservation**](VimResourceReservationApi.md#queryNetworkReservation) | **GET** /reservations/network_resources | 
[**terminateComputeReservation**](VimResourceReservationApi.md#terminateComputeReservation) | **DELETE** /reservations/compute_resources | 
[**terminateNetworkReservation**](VimResourceReservationApi.md#terminateNetworkReservation) | **DELETE** /reservations/network_resources | 


<a name="createComputeReservation"></a>
# **createComputeReservation**
> ReservedVirtualCompute createComputeReservation(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceReservationApi;


VimResourceReservationApi apiInstance = new VimResourceReservationApi();
CreateComputeResourceReservationRequest body = new CreateComputeResourceReservationRequest(); // CreateComputeResourceReservationRequest | 
try {
    ReservedVirtualCompute result = apiInstance.createComputeReservation(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceReservationApi#createComputeReservation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateComputeResourceReservationRequest**](CreateComputeResourceReservationRequest.md)|  |

### Return type

[**ReservedVirtualCompute**](ReservedVirtualCompute.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createNetworkReservation"></a>
# **createNetworkReservation**
> ReservedVirtualNetwork createNetworkReservation(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceReservationApi;


VimResourceReservationApi apiInstance = new VimResourceReservationApi();
CreateNetworkResourceReservationRequest body = new CreateNetworkResourceReservationRequest(); // CreateNetworkResourceReservationRequest | 
try {
    ReservedVirtualNetwork result = apiInstance.createNetworkReservation(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceReservationApi#createNetworkReservation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateNetworkResourceReservationRequest**](CreateNetworkResourceReservationRequest.md)|  |

### Return type

[**ReservedVirtualNetwork**](ReservedVirtualNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryComputeReservation"></a>
# **queryComputeReservation**
> List&lt;ReservedVirtualCompute&gt; queryComputeReservation()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceReservationApi;


VimResourceReservationApi apiInstance = new VimResourceReservationApi();
try {
    List<ReservedVirtualCompute> result = apiInstance.queryComputeReservation();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceReservationApi#queryComputeReservation");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ReservedVirtualCompute&gt;**](ReservedVirtualCompute.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="queryNetworkReservation"></a>
# **queryNetworkReservation**
> List&lt;ReservedVirtualNetwork&gt; queryNetworkReservation()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceReservationApi;


VimResourceReservationApi apiInstance = new VimResourceReservationApi();
try {
    List<ReservedVirtualNetwork> result = apiInstance.queryNetworkReservation();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceReservationApi#queryNetworkReservation");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ReservedVirtualNetwork&gt;**](ReservedVirtualNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="terminateComputeReservation"></a>
# **terminateComputeReservation**
> List&lt;String&gt; terminateComputeReservation(reservationId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceReservationApi;


VimResourceReservationApi apiInstance = new VimResourceReservationApi();
List<String> reservationId = Arrays.asList("reservationId_example"); // List<String> | Identifier of the resource reservation(s) to terminate.
try {
    List<String> result = apiInstance.terminateComputeReservation(reservationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceReservationApi#terminateComputeReservation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **reservationId** | [**List&lt;String&gt;**](String.md)| Identifier of the resource reservation(s) to terminate. |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="terminateNetworkReservation"></a>
# **terminateNetworkReservation**
> List&lt;String&gt; terminateNetworkReservation(reservationId)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimResourceReservationApi;


VimResourceReservationApi apiInstance = new VimResourceReservationApi();
List<String> reservationId = Arrays.asList("reservationId_example"); // List<String> | Identifier of the resource reservation(s) to terminate.
try {
    List<String> result = apiInstance.terminateNetworkReservation(reservationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimResourceReservationApi#terminateNetworkReservation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **reservationId** | [**List&lt;String&gt;**](String.md)| Identifier of the resource reservation(s) to terminate. |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

