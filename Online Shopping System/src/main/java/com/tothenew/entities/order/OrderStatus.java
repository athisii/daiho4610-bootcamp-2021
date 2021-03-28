package com.tothenew.entities.order;

import com.tothenew.entities.order.orderstatusenum.FromStatus;
import com.tothenew.entities.order.orderstatusenum.ToStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    private Long id;
    private FromStatus fromStatus;
    private ToStatus toStatus;
    private String transitionNotesComments;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;
}
