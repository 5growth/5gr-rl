
# Dashboard

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**dashboardId** | **String** | the ID assigned to the dashboard |  [optional]
**url** | **String** | the URL through which the dashboard is reachable |  [optional]
**name** | **String** | the name of the dashboard |  [optional]
**panels** | [**List&lt;DashboardPanel&gt;**](DashboardPanel.md) | the panels to be included in the dashboard |  [optional]
**users** | **List&lt;String&gt;** | the users which should be allowed to see this dashboard |  [optional]
**plottedTime** | **Integer** | the time interval to be shown on the graphs, in minutes |  [optional]
**refreshTime** | [**RefreshTimeEnum**](#RefreshTimeEnum) | the time interval to wait before refreshing the graphs |  [optional]


<a name="RefreshTimeEnum"></a>
## Enum: RefreshTimeEnum
Name | Value
---- | -----
OFF | &quot;off&quot;
_5S | &quot;5s&quot;
_10S | &quot;10s&quot;
_30S | &quot;30s&quot;
_1M | &quot;1m&quot;
_5M | &quot;5m&quot;
_15M | &quot;15m&quot;
_30M | &quot;30m&quot;
_1H | &quot;1h&quot;
_2H | &quot;2h&quot;
_1D | &quot;1d&quot;



