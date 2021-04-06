package com.tothenew.entities.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ProductReviewKey implements Serializable {
    private Long productId;
    private Long customerId;
}
