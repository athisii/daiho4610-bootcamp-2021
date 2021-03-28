package com.tothenew.entities.product;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ProductReviewKey implements Serializable {
    private Long productId;
    private Long customerId;
}
