package com.tothenew.status;

import com.tothenew.entities.order.orderstatusenum.FromStatus;
import com.tothenew.entities.order.orderstatusenum.ToStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderStatusMap {
    public Map<FromStatus, List<ToStatus>> getStatusList() {
        Map<FromStatus, List<ToStatus>> statusList = new HashMap<>();
        statusList.put(FromStatus.ORDER_PLACED, List.of(ToStatus.CANCELLED, ToStatus.ORDER_CONFIRMED, ToStatus.ORDER_REJECTED));
        statusList.put(FromStatus.CANCELLED, List.of(ToStatus.REFUND_INITIATED, ToStatus.CLOSED));
        statusList.put(FromStatus.ORDER_REJECTED, List.of(ToStatus.REFUND_INITIATED, ToStatus.CLOSED));
        statusList.put(FromStatus.ORDER_CONFIRMED, List.of(ToStatus.CANCELLED, ToStatus.ORDER_SHIPPED));
        statusList.put(FromStatus.ORDER_SHIPPED, List.of(ToStatus.DELIVERED));
        statusList.put(FromStatus.DELIVERED, List.of(ToStatus.RETURN_REQUESTED, ToStatus.CLOSED));
        statusList.put(FromStatus.RETURN_REQUESTED, List.of(ToStatus.RETURN_REJECTED, ToStatus.RETURN_APPROVED));
        statusList.put(FromStatus.RETURN_REJECTED, List.of(ToStatus.CLOSED));
        statusList.put(FromStatus.RETURN_APPROVED, List.of(ToStatus.PICK_UP_INITIATED));
        statusList.put(FromStatus.PICK_UP_INITIATED, List.of(ToStatus.PICK_UP_COMPLETED));
        statusList.put(FromStatus.PICK_UP_COMPLETED, List.of(ToStatus.REFUND_INITIATED));
        statusList.put(FromStatus.REFUND_INITIATED, List.of(ToStatus.REFUND_COMPLETED));
        statusList.put(FromStatus.REFUND_COMPLETED, List.of(ToStatus.CLOSED));
        return statusList;
    }
}