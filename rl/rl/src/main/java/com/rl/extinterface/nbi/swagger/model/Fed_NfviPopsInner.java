package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributes;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Fed_NfviPopsInner   {
  
  private @Valid Fed_NfviPopsInnerNfviPopAttributes nfviPopAttributes = null;

  /**
   **/
  public Fed_NfviPopsInner nfviPopAttributes(Fed_NfviPopsInnerNfviPopAttributes nfviPopAttributes) {
    this.nfviPopAttributes = nfviPopAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("nfviPopAttributes")
  @NotNull
  public Fed_NfviPopsInnerNfviPopAttributes getNfviPopAttributes() {
    return nfviPopAttributes;
  }
  public void setNfviPopAttributes(Fed_NfviPopsInnerNfviPopAttributes nfviPopAttributes) {
    this.nfviPopAttributes = nfviPopAttributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Fed_NfviPopsInner nfviPopsInner = (Fed_NfviPopsInner) o;
    return Objects.equals(nfviPopAttributes, nfviPopsInner.nfviPopAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nfviPopAttributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInner {\n");
    
    sb.append("    nfviPopAttributes: ").append(toIndentedString(nfviPopAttributes)).append("\n");
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

