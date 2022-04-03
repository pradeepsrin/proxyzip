package com.flipkart.payments.proxyzipper.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HeaderBasedZipperConfig.class, name = "headerBased")
})
public abstract class ZipperConfig {

    @NotNull
    private String proxyTo;

    @NotNull
    private String type;

    public String getProxyTo() {
        return proxyTo;
    }

    public void setProxyTo(String proxyTo) {
        this.proxyTo = proxyTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
