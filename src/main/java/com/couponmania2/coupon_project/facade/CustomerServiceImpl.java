package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.beans.Purchase;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final CouponRepo couponRepo;
    private final PurchaseRepo purchaseRepo;

    /**
     * Checks if given credentials match customer login credentials.
     *
     * @param userName   user email.
     * @param userPass   user password.
     * @param clientType the type of the client.
     * @return returns found company if credentials match a customer in the database.
     * @throws AppUnauthorizedRequestException if credentials dont match any customer in the database.
     */
    @Override
    public Customer checkCredentials(String userName, String userPass, ClientType clientType) throws AppUnauthorizedRequestException {
        if (customerRepo.findByEmailAndPassword(userName, userPass).isEmpty() || !(clientType.equals(ClientType.customer))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return customerRepo.findByEmailAndPassword(userName, userPass).get();
    }

    /**
     * adds a coupon purchase to the database by objects.
     *
     * @param coupon   the coupon to purchase.
     * @param customer the customer purchasing
     * @throws AppTargetExistsException if the customer already have coupon of this type.
     */
    @Override
    public void purchaseCoupon(Coupon coupon, Customer customer) throws AppTargetExistsException {
        if (purchaseRepo.findByCustomerAndCoupon(customer, coupon).isPresent()) {
            throw new AppTargetExistsException(AppTargetExistsMessage.COUPON_EXISTS);
        }
        purchaseRepo.save(new Purchase(customer, coupon));
    }

    /**
     * adds a coupon purchase to the database by id.
     *
     * @param couponId   the id of the coupon to purchase.
     * @param customerId the id of the customer to purchase.
     * @throws AppTargetExistsException   if the customer already have coupon of this type.
     * @throws AppTargetNotFoundException if the customer or coupon dont exist in the database.
     */
    @Override
    public void purchaseCoupon(long couponId, long customerId) throws AppTargetExistsException, AppTargetNotFoundException {
        if (purchaseRepo.findByCustomerAndCoupon(customerRepo.getById(customerId), couponRepo.getById(couponId)).isPresent()) {
            throw new AppTargetExistsException(AppTargetExistsMessage.COUPON_EXISTS);
        }
        if (customerRepo.findById(customerId).isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        if (couponRepo.findById(couponId).isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COUPON_NOT_FOUND);
        }
        purchaseRepo.save(new Purchase(customerRepo.getById(customerId), couponRepo.getById(couponId)));
    }

    /**
     * gets all coupons from the database.
     *
     * @return all of the coupons.
     */
    @Override
    public Set<Coupon> getAllCoupons() {
        return new HashSet<>(couponRepo.findAll());
    }

    /**
     * gets all the coupons the customer owns.
     *
     * @param customerId the id of the customer to find the coupons of.
     * @return set of all coupons the customer owns.
     * @throws AppTargetNotFoundException if the customer dont exist in the database.
     */
    @Override
    public Set<Coupon> getCustomerCoupons(long customerId) throws AppTargetNotFoundException {
        if (customerRepo.findById(customerId).isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        return purchaseRepo.getAllCouponsOfCustomer(customerRepo.getById(customerId));
    }

    /**
     * gets all the coupons of the customer by category.
     *
     * @param customerId the id of the customer to fidn the coupons of.
     * @param category   the category to filter by.
     * @return set of all coupons the customer owns by given condition.
     * @throws AppTargetNotFoundException if customer doesnt exist in the database.
     */
    @Override
    public Set<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws AppTargetNotFoundException {
        if (!customerRepo.existsById(customerId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        return couponRepo.findByCategory(category);
    }

    /**
     * gets all the coupons of the customer by max price.
     *
     * @param customerId the id of the customer to find the coupons of.
     * @param maxPrice   the max price to filter by.
     * @return set of all coupons the customer owns by given condition.
     * @throws AppTargetNotFoundException if customer doesnt exist in the database.
     */
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

    /**
     * retrieves a customer from the database.
     *
     * @param customerId the id of the customer to retrieve.
     * @return the customer found in the database.
     * @throws AppTargetNotFoundException if customer id doesnt exist in the database.
     */
    @Override
    public Customer getCustomerDetails(long customerId) throws AppTargetNotFoundException {
        if (customerRepo.findById(customerId).isEmpty() || !customerRepo.existsById(customerId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        return customerRepo.getById(customerId);
    }
}
