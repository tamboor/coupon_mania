package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.beans.Purchase;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestMessage;
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
    protected final CompanyRepo companyRepo;
    protected final CustomerRepo customerRepo;
    protected final CouponRepo couponRepo;
    protected final PurchaseRepo purchaseRepo;

    @Override
    public long checkCredentials(String userName, String userPass, ClientType clientType) throws AppUnauthorizedRequestException {
        if (customerRepo.findByEmailAndPassword(userName,userPass).isEmpty() || !(clientType.equals(ClientType.CUSTOMER))){
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return customerRepo.findByEmailAndPassword(userName,userPass).get().getId();
    }

    //todo: add validation
    @Override
    public void purchaseCoupon(Coupon coupon, Customer customer) {
        if (purchaseRepo.findByCustomerAndCoupon(customer, coupon) != null) {
            //todo: add custom exception.
        }
        purchaseRepo.save(new Purchase(customer, coupon));
    }

    @Override
    public void purchaseCoupon(long couponId, long customerId) {
        if (purchaseRepo.findByCustomerAndCoupon(customerRepo.getById(customerId), couponRepo.getById(couponId)) != null) {
            //todo: add custom exception.
        }
        purchaseRepo.save(new Purchase(customerRepo.getById(customerId), couponRepo.getById(couponId)));
    }

    @Override
    public Set<Coupon> getCustomerCoupons(long customerId) {
        if (customerRepo.findById(customerId).isEmpty()) {
            //todo: add custom exception if customer doesn't exist.
        }
        return purchaseRepo.getAllCouponsOfCustomer(customerRepo.getById(customerId));
    }

    @Override
    public Set<Coupon> getCustomerCouponsByCategory(long customerId, Category category) {
        if (!customerRepo.existsById(customerId)){
            //todo: add custom exception
        }
        return purchaseRepo.getCouponsOfCustomerByCategory(customerRepo.getById(customerId), category);
    }

    @Override
    public Set<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) {
        if (!customerRepo.existsById(customerId)){
            //todo: add custom exception
        }
        if (maxPrice <= 0) {
            //todo: add custom exception: price cannot be below 0.
        }
        return purchaseRepo.getCouponsOfCustomerByMaxPrice(customerRepo.getById(customerId), maxPrice);
    }

    @Override
    public Customer getCustomerDetails(long customerId) {
        if (customerRepo.findById(customerId).isEmpty()) {
            //todo: add custom exception if customer doesn't exist.
        }
        return customerRepo.getById(customerId);
    }
}
