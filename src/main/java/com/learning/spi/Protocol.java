package com.learning.spi;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Protocol {
    
    void refer();
    
    void export();
}
