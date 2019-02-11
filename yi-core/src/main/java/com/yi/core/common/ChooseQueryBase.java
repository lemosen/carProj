package com.yi.core.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class ChooseQueryBase implements Serializable {
    private String queryText;

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
