package com.flipkart.payments.proxyzipper.config;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Data
public class ProxyPipelineConfig {

    @NotNull
    private String pageKey;

    @Nullable
    private CallDefinition pre, post;

}
