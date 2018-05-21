package com.learning.proxy.mapper;

import java.lang.reflect.Proxy;

public class MapperProxyFactory<T> {
    
    private Class<T> mapperInterface;
    
    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }
    
    public T newInstance() {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new MapperProxy<T>());
    }
}
