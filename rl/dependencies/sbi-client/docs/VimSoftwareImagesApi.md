# VimSoftwareImagesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addSoftwareImage**](VimSoftwareImagesApi.md#addSoftwareImage) | **POST** /software_images | 
[**deleteSoftwareImage**](VimSoftwareImagesApi.md#deleteSoftwareImage) | **DELETE** /software_images/{id} | 
[**querySoftwareImage**](VimSoftwareImagesApi.md#querySoftwareImage) | **GET** /software_images/{id} | 
[**querySoftwareImages**](VimSoftwareImagesApi.md#querySoftwareImages) | **GET** /software_images | 


<a name="addSoftwareImage"></a>
# **addSoftwareImage**
> SoftwareImageInformation addSoftwareImage(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimSoftwareImagesApi;


VimSoftwareImagesApi apiInstance = new VimSoftwareImagesApi();
SoftwareImageAddQuery body = new SoftwareImageAddQuery(); // SoftwareImageAddQuery | 
try {
    SoftwareImageInformation result = apiInstance.addSoftwareImage(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimSoftwareImagesApi#addSoftwareImage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SoftwareImageAddQuery**](SoftwareImageAddQuery.md)|  |

### Return type

[**SoftwareImageInformation**](SoftwareImageInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteSoftwareImage"></a>
# **deleteSoftwareImage**
> String deleteSoftwareImage(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimSoftwareImagesApi;


VimSoftwareImagesApi apiInstance = new VimSoftwareImagesApi();
String id = "id_example"; // String | The identifier of the software image to be deleted.
try {
    String result = apiInstance.deleteSoftwareImage(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimSoftwareImagesApi#deleteSoftwareImage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The identifier of the software image to be deleted. |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="querySoftwareImage"></a>
# **querySoftwareImage**
> SoftwareImageInformation querySoftwareImage(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimSoftwareImagesApi;


VimSoftwareImagesApi apiInstance = new VimSoftwareImagesApi();
String id = "id_example"; // String | The identifier of the software image to be queried.
try {
    SoftwareImageInformation result = apiInstance.querySoftwareImage(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimSoftwareImagesApi#querySoftwareImage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The identifier of the software image to be queried. |

### Return type

[**SoftwareImageInformation**](SoftwareImageInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="querySoftwareImages"></a>
# **querySoftwareImages**
> List&lt;SoftwareImageInformation&gt; querySoftwareImages()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VimSoftwareImagesApi;


VimSoftwareImagesApi apiInstance = new VimSoftwareImagesApi();
try {
    List<SoftwareImageInformation> result = apiInstance.querySoftwareImages();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VimSoftwareImagesApi#querySoftwareImages");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;SoftwareImageInformation&gt;**](SoftwareImageInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

