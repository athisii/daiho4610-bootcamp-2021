package com.tothenew.objects.categorymetadata;

import lombok.Getter;

import java.util.Objects;

@Getter
public class MetadataFieldValue {
    private String value;

    @Override
    public String toString() {
        return "MetadataFieldValue{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataFieldValue that = (MetadataFieldValue) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}