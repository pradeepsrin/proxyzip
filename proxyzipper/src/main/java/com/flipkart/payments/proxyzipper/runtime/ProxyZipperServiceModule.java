package com.flipkart.payments.proxyzipper.runtime;

import com.flipkart.payments.proxyzipper.config.ProxyZipperConfiguration;
import com.flipkart.payments.proxyzipper.zipper.Zipper;
import com.google.inject.AbstractModule;

public class ProxyZipperServiceModule extends AbstractModule {

    private final ProxyZipperConfiguration config;

    public ProxyZipperServiceModule(ProxyZipperConfiguration proxyZipperConfiguration) {
        this.config = proxyZipperConfiguration;
    }

    @Override
    protected void configure() {

        bind(Zipper.class)
                .toInstance(new Zipper(config.getZips(), config.getZipper()));

    }
}
