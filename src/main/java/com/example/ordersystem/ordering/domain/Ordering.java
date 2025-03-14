package com.example.ordersystem.ordering.domain;

import com.example.ordersystem.common.domain.BaseTimeEntity;
import com.example.ordersystem.member.domain.Member;
import com.example.ordersystem.ordering.dto.OrderDetailResDto;
import com.example.ordersystem.ordering.dto.OrderListResDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Ordering extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public OrderListResDto fromEntity(){
        List<OrderDetailResDto> orderDetailResDtos = new ArrayList<>();
        for(OrderDetail od : this.getOrderDetails()){
            OrderDetailResDto orderDetailResDto = OrderDetailResDto.builder()
                    .detailId(od.getId())
                    .productName(od.getProduct().getName())
                    .count(od.getQuantity())
                    .build();
            orderDetailResDtos.add(orderDetailResDto);
        }
        OrderListResDto orderDto = OrderListResDto
                .builder()
                .id(this.getId())
                .memberEmail(this.getMember().getEmail())
                .orderStatus(this.getOrderStatus().toString())
                .orderDetails(orderDetailResDtos)
                .build();
        return orderDto;
    }
    public void cancelStatus(){
        this.orderStatus = OrderStatus.CANCELED;
    }
}
