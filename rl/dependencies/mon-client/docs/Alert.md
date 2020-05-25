
# Alert

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**alertId** | **String** | the ID assigned to the alert |  [optional]
**query** | **String** | the query whose value should be monitored. See https://prometheus.io/docs/prometheus/latest/querying/basics/ for details  |  [optional]
**label** | **String** | the label that will be used in the notifications |  [optional]
**severity** | [**SeverityEnum**](#SeverityEnum) | the severity of the alert |  [optional]
**value** | **Double** | the value associated to the threshold |  [optional]
**kind** | [**KindEnum**](#KindEnum) | the kind of inequality the query should satisfy related to the value |  [optional]
**target** | **String** |  |  [optional]


<a name="SeverityEnum"></a>
## Enum: SeverityEnum
Name | Value
---- | -----
WARNING | &quot;warning&quot;
ERROR | &quot;error&quot;
CRITICAL | &quot;critical&quot;


<a name="KindEnum"></a>
## Enum: KindEnum
Name | Value
---- | -----
G | &quot;g&quot;
GEQ | &quot;geq&quot;
L | &quot;l&quot;
LEQ | &quot;leq&quot;
EQ | &quot;eq&quot;
NEQ | &quot;neq&quot;



