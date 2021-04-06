package com.tothenew.entities.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Getter
@Setter
public class CategoryMetadataFieldKey implements Serializable {
    private Long categoryMetadataFieldId;
    private Long categoryId;

}
