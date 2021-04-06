package com.tothenew.objects.categorymetadata;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
public class MetadataFieldIdValue {
    @NotNull
    private Long metadataFieldId;
    @NotNull
    @NotEmpty
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataFieldIdValue that = (MetadataFieldIdValue) o;
        return metadataFieldId.equals(that.metadataFieldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadataFieldId);
    }
}