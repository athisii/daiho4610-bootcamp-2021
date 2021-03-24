package com.tothenew.entities.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
public class CategoryMetadataFieldKey implements Serializable {
    private int categoryMetadataFieldId;
    private int categoryId;

}
