package com.flipkart.payments.proxyzipper.config;

import com.google.common.collect.ImmutableList;
import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProxyZipperConfiguration extends Configuration {

    @NotNull
    private ImmutableList<ProxyPipelineConfig> zips;

    @NotNull
    private ZipperConfig zipper;

}
