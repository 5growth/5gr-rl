package com.ericsson.radioplugin.nbi.swagger.model;

import com.ericsson.radioplugin.nbi.swagger.model.AppExternalCpd;
import com.ericsson.radioplugin.nbi.swagger.model.ChangeAppInstanceStateOpConfig;
import com.ericsson.radioplugin.nbi.swagger.model.DNSRuleDescriptor;
import com.ericsson.radioplugin.nbi.swagger.model.LatencyDescriptor;
import com.ericsson.radioplugin.nbi.swagger.model.ServiceDependency;
import com.ericsson.radioplugin.nbi.swagger.model.SwImageDescriptor;
import com.ericsson.radioplugin.nbi.swagger.model.TerminateAppInstanceOpConfig;
import com.ericsson.radioplugin.nbi.swagger.model.TrafficRuleDescriptor;
import com.ericsson.radioplugin.nbi.swagger.model.TransportDependency;
import com.ericsson.radioplugin.nbi.swagger.model.VirtualComputeDescription;
import com.ericsson.radioplugin.nbi.swagger.model.VirtualStorageDescriptor;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AppD   {
  
  private @Valid String appDId = null;
  private @Valid String appName = null;
  private @Valid String appProvider = null;
  private @Valid String appSoftVersion = null;
  private @Valid String appDVersion = null;
  private @Valid List<String> mecVersion = new ArrayList<String>();
  private @Valid String appInfoName = null;
  private @Valid String appDescription = null;
  private @Valid VirtualComputeDescription virtualComputeDescriptor = null;
  private @Valid List<SwImageDescriptor> swImageDescriptor = new ArrayList<SwImageDescriptor>();
  private @Valid List<VirtualStorageDescriptor> virtualStorageDescriptor = new ArrayList<VirtualStorageDescriptor>();
  private @Valid List<AppExternalCpd> appExtCpd = new ArrayList<AppExternalCpd>();
  private @Valid List<ServiceDependency> appServiceRequired = new ArrayList<ServiceDependency>();
  private @Valid List<ServiceDependency> appServiceOptional = new ArrayList<ServiceDependency>();
  private @Valid List<TransportDependency> transportDependencies = new ArrayList<TransportDependency>();
  private @Valid List<TrafficRuleDescriptor> appTrafficRule = new ArrayList<TrafficRuleDescriptor>();
  private @Valid List<DNSRuleDescriptor> appDNSRule = new ArrayList<DNSRuleDescriptor>();
  private @Valid LatencyDescriptor appLatency = null;
  private @Valid TerminateAppInstanceOpConfig terminateAppInstanceOpConfig = null;
  private @Valid ChangeAppInstanceStateOpConfig changeAppInstanceStateOpConfig = null;

  /**
   * Identifier of this mobile edge application descriptor. This attribute shall be globally unique.
   **/
  public AppD appDId(String appDId) {
    this.appDId = appDId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of this mobile edge application descriptor. This attribute shall be globally unique.")
  @JsonProperty("appDId")
  @NotNull
  public String getAppDId() {
    return appDId;
  }
  public void setAppDId(String appDId) {
    this.appDId = appDId;
  }

  /**
   * Name to identify the mobile edge application.
   **/
  public AppD appName(String appName) {
    this.appName = appName;
    return this;
  }

  
  @ApiModelProperty(value = "Name to identify the mobile edge application.")
  @JsonProperty("appName")
  public String getAppName() {
    return appName;
  }
  public void setAppName(String appName) {
    this.appName = appName;
  }

  /**
   * Provider of the application and of the AppD.
   **/
  public AppD appProvider(String appProvider) {
    this.appProvider = appProvider;
    return this;
  }

  
  @ApiModelProperty(value = "Provider of the application and of the AppD.")
  @JsonProperty("appProvider")
  public String getAppProvider() {
    return appProvider;
  }
  public void setAppProvider(String appProvider) {
    this.appProvider = appProvider;
  }

  /**
   * AvailableIdentifies the version of software of the mobile edge application.
   **/
  public AppD appSoftVersion(String appSoftVersion) {
    this.appSoftVersion = appSoftVersion;
    return this;
  }

  
  @ApiModelProperty(value = "AvailableIdentifies the version of software of the mobile edge application.")
  @JsonProperty("appSoftVersion")
  public String getAppSoftVersion() {
    return appSoftVersion;
  }
  public void setAppSoftVersion(String appSoftVersion) {
    this.appSoftVersion = appSoftVersion;
  }

  /**
   * Identifies the version of the application descriptor.
   **/
  public AppD appDVersion(String appDVersion) {
    this.appDVersion = appDVersion;
    return this;
  }

  
  @ApiModelProperty(value = "Identifies the version of the application descriptor.")
  @JsonProperty("appDVersion")
  public String getAppDVersion() {
    return appDVersion;
  }
  public void setAppDVersion(String appDVersion) {
    this.appDVersion = appDVersion;
  }

  /**
   * Identifies version(s) of ME system compatible with the mobile edge application described in this version of the AppD.
   **/
  public AppD mecVersion(List<String> mecVersion) {
    this.mecVersion = mecVersion;
    return this;
  }

  
  @ApiModelProperty(value = "Identifies version(s) of ME system compatible with the mobile edge application described in this version of the AppD.")
  @JsonProperty("mecVersion")
  public List<String> getMecVersion() {
    return mecVersion;
  }
  public void setMecVersion(List<String> mecVersion) {
    this.mecVersion = mecVersion;
  }

  /**
   * Human readable name for the ME application product. May change during the ME application product lifetime.
   **/
  public AppD appInfoName(String appInfoName) {
    this.appInfoName = appInfoName;
    return this;
  }

  
  @ApiModelProperty(value = "Human readable name for the ME application product. May change during the ME application product lifetime.")
  @JsonProperty("appInfoName")
  public String getAppInfoName() {
    return appInfoName;
  }
  public void setAppInfoName(String appInfoName) {
    this.appInfoName = appInfoName;
  }

  /**
   * Human readable description of the mobile edge application.
   **/
  public AppD appDescription(String appDescription) {
    this.appDescription = appDescription;
    return this;
  }

  
  @ApiModelProperty(value = "Human readable description of the mobile edge application.")
  @JsonProperty("appDescription")
  public String getAppDescription() {
    return appDescription;
  }
  public void setAppDescription(String appDescription) {
    this.appDescription = appDescription;
  }

  /**
   **/
  public AppD virtualComputeDescriptor(VirtualComputeDescription virtualComputeDescriptor) {
    this.virtualComputeDescriptor = virtualComputeDescriptor;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("virtualComputeDescriptor")
  public VirtualComputeDescription getVirtualComputeDescriptor() {
    return virtualComputeDescriptor;
  }
  public void setVirtualComputeDescriptor(VirtualComputeDescription virtualComputeDescriptor) {
    this.virtualComputeDescriptor = virtualComputeDescriptor;
  }

  /**
   **/
  public AppD swImageDescriptor(List<SwImageDescriptor> swImageDescriptor) {
    this.swImageDescriptor = swImageDescriptor;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("swImageDescriptor")
  public List<SwImageDescriptor> getSwImageDescriptor() {
    return swImageDescriptor;
  }
  public void setSwImageDescriptor(List<SwImageDescriptor> swImageDescriptor) {
    this.swImageDescriptor = swImageDescriptor;
  }

  /**
   **/
  public AppD virtualStorageDescriptor(List<VirtualStorageDescriptor> virtualStorageDescriptor) {
    this.virtualStorageDescriptor = virtualStorageDescriptor;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("virtualStorageDescriptor")
  public List<VirtualStorageDescriptor> getVirtualStorageDescriptor() {
    return virtualStorageDescriptor;
  }
  public void setVirtualStorageDescriptor(List<VirtualStorageDescriptor> virtualStorageDescriptor) {
    this.virtualStorageDescriptor = virtualStorageDescriptor;
  }

  /**
   **/
  public AppD appExtCpd(List<AppExternalCpd> appExtCpd) {
    this.appExtCpd = appExtCpd;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("appExtCpd")
  public List<AppExternalCpd> getAppExtCpd() {
    return appExtCpd;
  }
  public void setAppExtCpd(List<AppExternalCpd> appExtCpd) {
    this.appExtCpd = appExtCpd;
  }

  /**
   * Describes services a mobile edge application requires to run.
   **/
  public AppD appServiceRequired(List<ServiceDependency> appServiceRequired) {
    this.appServiceRequired = appServiceRequired;
    return this;
  }

  
  @ApiModelProperty(value = "Describes services a mobile edge application requires to run.")
  @JsonProperty("appServiceRequired")
  public List<ServiceDependency> getAppServiceRequired() {
    return appServiceRequired;
  }
  public void setAppServiceRequired(List<ServiceDependency> appServiceRequired) {
    this.appServiceRequired = appServiceRequired;
  }

  /**
   * Describes services a mobile edge application may use if available.
   **/
  public AppD appServiceOptional(List<ServiceDependency> appServiceOptional) {
    this.appServiceOptional = appServiceOptional;
    return this;
  }

  
  @ApiModelProperty(value = "Describes services a mobile edge application may use if available.")
  @JsonProperty("appServiceOptional")
  public List<ServiceDependency> getAppServiceOptional() {
    return appServiceOptional;
  }
  public void setAppServiceOptional(List<ServiceDependency> appServiceOptional) {
    this.appServiceOptional = appServiceOptional;
  }

  /**
   * Transports, if any, that this application requires to be provided by the platform. These transports will be used by the application to deliver services provided by this application. Only relevant for service-producing apps.
   **/
  public AppD transportDependencies(List<TransportDependency> transportDependencies) {
    this.transportDependencies = transportDependencies;
    return this;
  }

  
  @ApiModelProperty(value = "Transports, if any, that this application requires to be provided by the platform. These transports will be used by the application to deliver services provided by this application. Only relevant for service-producing apps.")
  @JsonProperty("transportDependencies")
  public List<TransportDependency> getTransportDependencies() {
    return transportDependencies;
  }
  public void setTransportDependencies(List<TransportDependency> transportDependencies) {
    this.transportDependencies = transportDependencies;
  }

  /**
   * Describes traffic rules the mobile edge application requires.
   **/
  public AppD appTrafficRule(List<TrafficRuleDescriptor> appTrafficRule) {
    this.appTrafficRule = appTrafficRule;
    return this;
  }

  
  @ApiModelProperty(value = "Describes traffic rules the mobile edge application requires.")
  @JsonProperty("appTrafficRule")
  public List<TrafficRuleDescriptor> getAppTrafficRule() {
    return appTrafficRule;
  }
  public void setAppTrafficRule(List<TrafficRuleDescriptor> appTrafficRule) {
    this.appTrafficRule = appTrafficRule;
  }

  /**
   * Describes DNS rules the mobile edge application requires.
   **/
  public AppD appDNSRule(List<DNSRuleDescriptor> appDNSRule) {
    this.appDNSRule = appDNSRule;
    return this;
  }

  
  @ApiModelProperty(value = "Describes DNS rules the mobile edge application requires.")
  @JsonProperty("appDNSRule")
  public List<DNSRuleDescriptor> getAppDNSRule() {
    return appDNSRule;
  }
  public void setAppDNSRule(List<DNSRuleDescriptor> appDNSRule) {
    this.appDNSRule = appDNSRule;
  }

  /**
   **/
  public AppD appLatency(LatencyDescriptor appLatency) {
    this.appLatency = appLatency;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("appLatency")
  public LatencyDescriptor getAppLatency() {
    return appLatency;
  }
  public void setAppLatency(LatencyDescriptor appLatency) {
    this.appLatency = appLatency;
  }

  /**
   **/
  public AppD terminateAppInstanceOpConfig(TerminateAppInstanceOpConfig terminateAppInstanceOpConfig) {
    this.terminateAppInstanceOpConfig = terminateAppInstanceOpConfig;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("terminateAppInstanceOpConfig")
  public TerminateAppInstanceOpConfig getTerminateAppInstanceOpConfig() {
    return terminateAppInstanceOpConfig;
  }
  public void setTerminateAppInstanceOpConfig(TerminateAppInstanceOpConfig terminateAppInstanceOpConfig) {
    this.terminateAppInstanceOpConfig = terminateAppInstanceOpConfig;
  }

  /**
   **/
  public AppD changeAppInstanceStateOpConfig(ChangeAppInstanceStateOpConfig changeAppInstanceStateOpConfig) {
    this.changeAppInstanceStateOpConfig = changeAppInstanceStateOpConfig;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("changeAppInstanceStateOpConfig")
  public ChangeAppInstanceStateOpConfig getChangeAppInstanceStateOpConfig() {
    return changeAppInstanceStateOpConfig;
  }
  public void setChangeAppInstanceStateOpConfig(ChangeAppInstanceStateOpConfig changeAppInstanceStateOpConfig) {
    this.changeAppInstanceStateOpConfig = changeAppInstanceStateOpConfig;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppD appD = (AppD) o;
    return Objects.equals(appDId, appD.appDId) &&
        Objects.equals(appName, appD.appName) &&
        Objects.equals(appProvider, appD.appProvider) &&
        Objects.equals(appSoftVersion, appD.appSoftVersion) &&
        Objects.equals(appDVersion, appD.appDVersion) &&
        Objects.equals(mecVersion, appD.mecVersion) &&
        Objects.equals(appInfoName, appD.appInfoName) &&
        Objects.equals(appDescription, appD.appDescription) &&
        Objects.equals(virtualComputeDescriptor, appD.virtualComputeDescriptor) &&
        Objects.equals(swImageDescriptor, appD.swImageDescriptor) &&
        Objects.equals(virtualStorageDescriptor, appD.virtualStorageDescriptor) &&
        Objects.equals(appExtCpd, appD.appExtCpd) &&
        Objects.equals(appServiceRequired, appD.appServiceRequired) &&
        Objects.equals(appServiceOptional, appD.appServiceOptional) &&
        Objects.equals(transportDependencies, appD.transportDependencies) &&
        Objects.equals(appTrafficRule, appD.appTrafficRule) &&
        Objects.equals(appDNSRule, appD.appDNSRule) &&
        Objects.equals(appLatency, appD.appLatency) &&
        Objects.equals(terminateAppInstanceOpConfig, appD.terminateAppInstanceOpConfig) &&
        Objects.equals(changeAppInstanceStateOpConfig, appD.changeAppInstanceStateOpConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appDId, appName, appProvider, appSoftVersion, appDVersion, mecVersion, appInfoName, appDescription, virtualComputeDescriptor, swImageDescriptor, virtualStorageDescriptor, appExtCpd, appServiceRequired, appServiceOptional, transportDependencies, appTrafficRule, appDNSRule, appLatency, terminateAppInstanceOpConfig, changeAppInstanceStateOpConfig);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppD {\n");
    
    sb.append("    appDId: ").append(toIndentedString(appDId)).append("\n");
    sb.append("    appName: ").append(toIndentedString(appName)).append("\n");
    sb.append("    appProvider: ").append(toIndentedString(appProvider)).append("\n");
    sb.append("    appSoftVersion: ").append(toIndentedString(appSoftVersion)).append("\n");
    sb.append("    appDVersion: ").append(toIndentedString(appDVersion)).append("\n");
    sb.append("    mecVersion: ").append(toIndentedString(mecVersion)).append("\n");
    sb.append("    appInfoName: ").append(toIndentedString(appInfoName)).append("\n");
    sb.append("    appDescription: ").append(toIndentedString(appDescription)).append("\n");
    sb.append("    virtualComputeDescriptor: ").append(toIndentedString(virtualComputeDescriptor)).append("\n");
    sb.append("    swImageDescriptor: ").append(toIndentedString(swImageDescriptor)).append("\n");
    sb.append("    virtualStorageDescriptor: ").append(toIndentedString(virtualStorageDescriptor)).append("\n");
    sb.append("    appExtCpd: ").append(toIndentedString(appExtCpd)).append("\n");
    sb.append("    appServiceRequired: ").append(toIndentedString(appServiceRequired)).append("\n");
    sb.append("    appServiceOptional: ").append(toIndentedString(appServiceOptional)).append("\n");
    sb.append("    transportDependencies: ").append(toIndentedString(transportDependencies)).append("\n");
    sb.append("    appTrafficRule: ").append(toIndentedString(appTrafficRule)).append("\n");
    sb.append("    appDNSRule: ").append(toIndentedString(appDNSRule)).append("\n");
    sb.append("    appLatency: ").append(toIndentedString(appLatency)).append("\n");
    sb.append("    terminateAppInstanceOpConfig: ").append(toIndentedString(terminateAppInstanceOpConfig)).append("\n");
    sb.append("    changeAppInstanceStateOpConfig: ").append(toIndentedString(changeAppInstanceStateOpConfig)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

