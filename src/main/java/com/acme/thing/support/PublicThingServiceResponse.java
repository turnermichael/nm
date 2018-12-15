package com.acme.thing.support;

import com.acme.thing.model.Thing;

import java.util.List;

public class PublicThingServiceResponse {
    private int count;
    private List<Thing> entries;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    List<Thing> getEntries() {
        return entries;
    }

    public void setEntries(List<Thing> entries) {
        this.entries = entries;
    }
}
