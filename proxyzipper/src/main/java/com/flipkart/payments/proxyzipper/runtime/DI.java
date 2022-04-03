package com.flipkart.payments.proxyzipper.runtime;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

public class DI {
    private static DI instance = null;

    private final Injector injector;

    public static DI di() {
        return instance;
    }

    public synchronized static void initialise(AbstractModule module) {
        instance = new DI(module);
    }


    private DI(AbstractModule module) {
        this.injector = Guice.createInjector(module);
    }


    public <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    public <T> T getInstance(Key<T> key) {
        return injector.getInstance(key);
    }

}
