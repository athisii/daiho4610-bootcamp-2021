package com.tothenew.entities.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
public class CategoryMetadataFieldKey implements Serializable {
    private Long categoryMetadataFieldId;
    private Long categoryId;

}
