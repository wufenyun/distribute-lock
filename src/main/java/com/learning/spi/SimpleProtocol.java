package com.learning.spi;

public class SimpleProtocol implements Protocol {

    @Override
    public void refer() {
        System.out.println("refer");
    }

    @Override
    public void export() {
        System.out.println("export");
    }

}
