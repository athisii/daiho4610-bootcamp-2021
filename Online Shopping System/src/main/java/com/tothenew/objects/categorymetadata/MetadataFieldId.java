package com.tothenew.objects.categorymetadata;

import lombok.Getter;

import java.util.Objects;

@Getter
public class MetadataFieldId {
    private Long id;

    @Override
    public String toString() {
        return "MetadataFieldId{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataFieldId that = (MetadataFieldId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}