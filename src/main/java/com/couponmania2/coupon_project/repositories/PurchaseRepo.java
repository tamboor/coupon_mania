package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepo extends JpaRepository<Purchase , Integer> {
}
