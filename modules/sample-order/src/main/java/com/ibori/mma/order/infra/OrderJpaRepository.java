package com.ibori.mma.order.infra;

import com.ibori.mma.order.domain.Order;
import com.ibori.mma.order.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface SpringDataOrderRepository extends JpaRepository<Order, Long> {}

@Repository
class OrderJpaRepository implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;

    OrderJpaRepository(SpringDataOrderRepository springDataOrderRepository) {
        this.springDataOrderRepository = springDataOrderRepository;
    }

    @Override
    public Order save(Order order) {
        return springDataOrderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return springDataOrderRepository.findById(id);
    }
}