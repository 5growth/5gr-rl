# AlertApi

All URIs are relative to *https://localhost:8080/prom-manager*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAlert**](AlertApi.md#deleteAlert) | **DELETE** /alert/{alertId} | Delete alert
[**getAlert**](AlertApi.md#getAlert) | **GET** /alert/{alertId} | Get alert details
[**getAllAlerts**](AlertApi.md#getAllAlerts) | **GET** /alert | Get all alerts
[**postAlert**](AlertApi.md#postAlert) | **POST** /alert | Create new alert
[**putAlert**](AlertApi.md#putAlert) | **PUT** /alert/{alertId} | Update alert


<a name="deleteAlert"></a>
# **deleteAlert**
> InlineResponse2001 deleteAlert(alertId)

Delete alert

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AlertApi;


AlertApi apiInstance = new AlertApi();
String alertId = "alertId_example"; // String | the ID of the alert
try {
    InlineResponse2001 result = apiInstance.deleteAlert(alertId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AlertApi#deleteAlert");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **alertId** | **String**| the ID of the alert |

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/x-yaml

<a name="getAlert"></a>
# **getAlert**
> Alert getAlert(alertId)

Get alert details

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AlertApi;


AlertApi apiInstance = new AlertApi();
String alertId = "alertId_example"; // String | the ID of the alert
try {
    Alert result = apiInstance.getAlert(alertId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AlertApi#getAlert");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **alertId** | **String**| the ID of the alert |

### Return type

[**Alert**](Alert.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/x-yaml

<a name="getAllAlerts"></a>
# **getAllAlerts**
> AlertQueryResult getAllAlerts()

Get all alerts

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AlertApi;


AlertApi apiInstance = new AlertApi();
try {
    AlertQueryResult result = apiInstance.getAllAlerts();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AlertApi#getAllAlerts");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AlertQueryResult**](AlertQueryResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/x-yaml

<a name="postAlert"></a>
# **postAlert**
> Alert postAlert(alert)

Create new alert

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AlertApi;


AlertApi apiInstance = new AlertApi();
AlertDescription alert = new AlertDescription(); // AlertDescription | the alert to be created
try {
    Alert result = apiInstance.postAlert(alert);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AlertApi#postAlert");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **alert** | [**AlertDescription**](AlertDescription.md)| the alert to be created |

### Return type

[**Alert**](Alert.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/x-yaml
 - **Accept**: application/json, application/x-yaml

<a name="putAlert"></a>
# **putAlert**
> Alert putAlert(alertId, alert)

Update alert

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AlertApi;


AlertApi apiInstance = new AlertApi();
String alertId = "alertId_example"; // String | the ID of the alert
Alert alert = new Alert(); // Alert | the alert properties to be updated
try {
    Alert result = apiInstance.putAlert(alertId, alert);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AlertApi#putAlert");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **alertId** | **String**| the ID of the alert |
 **alert** | [**Alert**](Alert.md)| the alert properties to be updated |

### Return type

[**Alert**](Alert.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/x-yaml
 - **Accept**: application/json, application/x-yaml

