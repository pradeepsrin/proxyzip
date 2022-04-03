package com.flipkart.payments.proxyzipper.service;

import com.flipkart.payments.proxyzipper.config.HeaderBasedZipperConfig;
import com.flipkart.payments.proxyzipper.config.ProxyZipperConfiguration;
import com.flipkart.payments.proxyzipper.resources.upstreamexample.*;
import com.flipkart.payments.proxyzipper.resources.HeaderBasedZipperResource;
import com.flipkart.payments.proxyzipper.runtime.DI;
import com.flipkart.payments.proxyzipper.runtime.ProxyZipperServiceModule;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import static com.flipkart.payments.proxyzipper.runtime.DI.di;

public class ProxyZipperApplication extends Application<ProxyZipperConfiguration> {

    public static final String SERVICE_NAME = "ProxyZipper";

    @Override
    public void run(ProxyZipperConfiguration proxyZipperConfiguration, Environment environment) throws Exception {
        DI.initialise(new ProxyZipperServiceModule(proxyZipperConfiguration));

        environment.jersey().register(di().getInstance(HeaderBasedZipperResource.class));
        environment.jersey().register(new UpstreamExampleResource((HeaderBasedZipperConfig) proxyZipperConfiguration.getZipper()));
        environment.jersey().register(new SensitiveServiceResource( (HeaderBasedZipperConfig) proxyZipperConfiguration.getZipper()));
    }

    @Override
    public String getName() {
        return SERVICE_NAME;
    }

    public static void main(String[] args) throws Exception {
        ProxyZipperApplication lpa = new ProxyZipperApplication();
        lpa.run(args);
    }

}
