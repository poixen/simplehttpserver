package com.example;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Custom bean for collecting count data
 */
@XmlRootElement
public class CounterBean {
    public String name;
    public int count;

    public CounterBean() {}

    public CounterBean(String name, int value) {
        this.name = name;
        this.count = value;
    }

}
