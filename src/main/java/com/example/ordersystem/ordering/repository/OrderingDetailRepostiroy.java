package com.example.ordersystem.ordering.repository;

import com.example.ordersystem.ordering.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderingDetailRepostiroy extends JpaRepository<OrderDetail, Long> {
    
}
