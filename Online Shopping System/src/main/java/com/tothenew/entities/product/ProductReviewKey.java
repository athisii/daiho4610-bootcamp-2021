package com.tothenew.entities.product;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ProductReviewKey implements Serializable {
    private int productId;
    private int customerId;
}
