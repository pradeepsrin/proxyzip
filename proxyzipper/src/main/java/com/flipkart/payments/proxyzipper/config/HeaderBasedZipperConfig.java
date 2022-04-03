package com.flipkart.payments.proxyzipper.config;

import javax.validation.constraints.NotNull;

public class HeaderBasedZipperConfig extends ZipperConfig {

    @NotNull
    private String pageKeyHeader, preCallSensitiveDataHeader, postCallSensitiveDataHeader,
            preCallSensitiveDataMaskedHeader, postCallSensitiveDataMaskedHeader;

    public String getPageKeyHeader() {
        return pageKeyHeader;
    }

    public void setPageKeyHeader(String pageKeyHeader) {
        this.pageKeyHeader = pageKeyHeader;
    }

    public String getPreCallSensitiveDataHeader() {
        return preCallSensitiveDataHeader;
    }

    public void setPreCallSensitiveDataHeader(String preCallSensitiveDataHeader) {
        this.preCallSensitiveDataHeader = preCallSensitiveDataHeader;
    }

    public String getPostCallSensitiveDataHeader() {
        return postCallSensitiveDataHeader;
    }

    public void setPostCallSensitiveDataHeader(String postCallSensitiveDataHeader) {
        this.postCallSensitiveDataHeader = postCallSensitiveDataHeader;
    }

    public String getPreCallSensitiveDataMaskedHeader() {
        return preCallSensitiveDataMaskedHeader;
    }

    public void setPreCallSensitiveDataMaskedHeader(String preCallSensitiveDataMaskedHeader) {
        this.preCallSensitiveDataMaskedHeader = preCallSensitiveDataMaskedHeader;
    }

    public String getPostCallSensitiveDataMaskedHeader() {
        return postCallSensitiveDataMaskedHeader;
    }

    public void setPostCallSensitiveDataMaskedHeader(String postCallSensitiveDataMaskedHeader) {
        this.postCallSensitiveDataMaskedHeader = postCallSensitiveDataMaskedHeader;
    }
}
