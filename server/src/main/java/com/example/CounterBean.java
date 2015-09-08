package com.example;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Custom bean for collecting count data
 */
@XmlRootElement
public class CounterBean {
    private String name;
    private int count;

    public CounterBean() {}

    public CounterBean(final String name, final int value) {
        this.name = name;
        this.count = value;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CounterBean that = (CounterBean) o;

        if (count != that.count) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + count;
        return result;
    }
}
