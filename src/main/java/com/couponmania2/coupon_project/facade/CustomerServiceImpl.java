package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.beans.Purchase;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
//todo: change to required args c'tor
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CustomerRepo customerRepo;
    @Autowired
    protected CouponRepo couponRepo;
    @Autowired
    protected PurchaseRepo purchaseRepo;

    //todo: add validation
    @Override
    public void purchaseCoupon(Coupon coupon, Customer customer) {
        if (purchaseRepo.findByCustomerAndCoupon(customer, coupon) != null) {
            //todo: add custom exception.
        }
        purchaseRepo.save(new Purchase(customer, coupon));
    }

    @Override
    public void purchaseCoupon(int couponId, int customerId) {
        if (purchaseRepo.findByCustomerAndCoupon(customerRepo.getById(customerId), couponRepo.getById(couponId)) != null) {
            //todo: add custom exception.
        }
        purchaseRepo.save(new Purchase(customerRepo.getById(customerId), couponRepo.getById(couponId)));
    }

    @Override
    public Set<Coupon> getCustomerCoupons(int customerId) {
        if (customerRepo.findById(customerId).isEmpty()) {
            //todo: add custom exception if customer doesn't exist.
        }
        return purchaseRepo.getAllCouponsOfCustomer(customerRepo.getById(customerId));
    }

    @Override
    public Set<Coupon> getCustomerCouponsByCategory(int customerId, Category category) {
        if (!customerRepo.existsById(customerId)){
            //todo: add custom exception
        }
        return purchaseRepo.getCouponsOfCustomerByCategory(customerRepo.getById(customerId), category);
    }

    @Override
    public Set<Coupon> getCustomerCouponsByMaxPrice(int customerId, double maxPrice) {
        if (!customerRepo.existsById(customerId)){
            //todo: add custom exception
        }
        if (maxPrice <= 0) {
            //todo: add custom exception: price cannot be below 0.
        }
        return purchaseRepo.getCouponsOfCustomerByMaxPrice(customerRepo.getById(customerId), maxPrice);
    }

    @Override
    public Customer getCustomerDetails(int customerId) {
        if (customerRepo.findById(customerId).isEmpty()) {
            //todo: add custom exception if customer doesn't exist.
        }
        return customerRepo.getById(customerId);
    }
}
