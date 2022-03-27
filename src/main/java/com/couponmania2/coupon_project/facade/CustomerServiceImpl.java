package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.beans.Purchase;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final CouponRepo couponRepo;
    private final PurchaseRepo purchaseRepo;

    @Override
    public Customer checkCredentials(String userName, String userPass, ClientType clientType) throws AppUnauthorizedRequestException {
        if (customerRepo.findByEmailAndPassword(userName, userPass).isEmpty() || !(clientType.equals(ClientType.CUSTOMER))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return customerRepo.findByEmailAndPassword(userName, userPass).get();
    }

    @Override
    public void purchaseCoupon(Coupon coupon, Customer customer) throws AppTargetExistsException {
        //todo: check if needed to change to Optional
        if (purchaseRepo.findByCustomerAndCoupon(customer, coupon) != null) {
            throw new AppTargetExistsException(AppTargetExistsMessage.COUPON_EXISTS);
        }
        purchaseRepo.save(new Purchase(customer, coupon));
    }

    @Override
    public void purchaseCoupon(long couponId, long customerId) throws AppTargetExistsException {
        if (purchaseRepo.findByCustomerAndCoupon(customerRepo.getById(customerId), couponRepo.getById(couponId)) != null) {
            throw new AppTargetExistsException(AppTargetExistsMessage.COUPON_EXISTS);
        }
        purchaseRepo.save(new Purchase(customerRepo.getById(customerId), couponRepo.getById(couponId)));
    }

    @Override
    public Set<Coupon> getCustomerCoupons(long customerId) throws AppTargetNotFoundException {
        if (customerRepo.findById(customerId).isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        return purchaseRepo.getAllCouponsOfCustomer(customerRepo.getById(customerId));
    }

    @Override
    public Set<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws AppTargetNotFoundException {
        if (!customerRepo.existsById(customerId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        return purchaseRepo.getCouponsOfCustomerByCategory(customerRepo.getById(customerId), category);
    }

    @Override
    public Set<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws AppTargetNotFoundException, AppInvalidInputException {
        if (!customerRepo.existsById(customerId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        if (maxPrice <= 0) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NEGATIVE_PRICE);
        }
        return purchaseRepo.getCouponsOfCustomerByMaxPrice(customerRepo.getById(customerId), maxPrice);
    }

    @Override
    public Customer getCustomerDetails(long customerId) throws AppTargetNotFoundException {
        if (customerRepo.findById(customerId).isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        System.out.println("HEREEEEE"+ customerRepo.getById(customerId));
        return customerRepo.getById(customerId);
    }
}
