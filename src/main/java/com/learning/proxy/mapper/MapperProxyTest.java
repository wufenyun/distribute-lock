package com.learning.proxy.mapper;

import org.junit.Test;

public class MapperProxyTest {
    
    @Test
    public void update() {
        MapperProxyFactory<UserMapper> fc = new MapperProxyFactory<>(UserMapper.class);
        UserMapper userMapper = fc.newInstance();
        userMapper.add("");
    }
}
