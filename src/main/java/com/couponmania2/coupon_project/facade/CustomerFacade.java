package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.beans.Purchase;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerFacade extends ClientFacade implements CustomerService {
    //todo: add validation
    @Override
    public void purchaseCoupon(Coupon coupon, Customer customer) {
        if (purchaseRepo.findByCustomerAndCoupon(customer, coupon) == null) {
            purchaseRepo.save(new Purchase(customer, coupon));
        } else {
            //todo: add custom exception.
        }

    }

    @Override
    public void purchaseCoupon(int couponId, int customerId) {
        if (purchaseRepo.findByCustomerAndCoupon(customerRepo.getById(customerId), couponRepo.getById(couponId)) == null) {
            purchaseRepo.save(new Purchase(customerRepo.getById(customerId), couponRepo.getById(couponId)));
        } else {
            //todo: add custom exception.
        }
    }

    @Override
    public Set<Coupon> getCustomerCoupons(int customerId) {
        if (customerRepo.findById(customerId).isEmpty()) {
    //todo: add custom exception if customer doesn't exist.
        }
        return purchaseRepo.getAllCouponsOfCustomer(customerRepo.getById(customerId));
    }

    @Override
    public Set<Coupon> getCustomerCouponsByCategory(Customer customer, Category category) {
        return purchaseRepo.getCouponsOfCustomerByCategory(customer, category);
    }

    @Override
    public Set<Coupon> getCustomerCouponsByMaxPrice(Customer customer, double maxPrice) {
        if (maxPrice <= 0) {
            //todo: add custom exception: price cannot be below 0.
        }
        return purchaseRepo.getCouponsOfCustomerByMaxPrice(customer, maxPrice);
    }

    @Override
    public Customer getCustomerDetails(int id) {
        if (customerRepo.findById(id).isEmpty()) {
            //todo: add custom exception if customer doesn't exist.
        }
        return customerRepo.getById(id);
    }
}
