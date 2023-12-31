package org.logitrack.repository;

import org.logitrack.entities.Customer;
import org.logitrack.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
