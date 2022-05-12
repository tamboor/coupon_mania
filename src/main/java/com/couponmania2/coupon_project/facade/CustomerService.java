package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public interface CustomerService {
    /**
     * Checks if given credentials match customer login credentials.
     *
     * @param userName   user email.
     * @param userPass   user password.
     * @param clientType the type of the client.
     * @return returns found company if credentials match a customer in the database.
     * @throws AppUnauthorizedRequestException if credentials dont match any customer in the database.
     */
    Customer checkCredentials(String userName, String userPass, ClientType clientType) throws AppUnauthorizedRequestException;

    /**
     * adds a coupon purchase to the database by objects.
     *
     * @param coupon   the coupon to purchase.
     * @param customer the customer purchasing
     * @throws AppTargetExistsException if the customer already have coupon of this type.
     */
    void purchaseCoupon(Coupon coupon , Customer customer) throws AppTargetExistsException;

    /**
     * adds a coupon purchase to the database by id.
     *
     * @param couponId   the id of the coupon to purchase.
     * @param customerId the id of the customer to purchase.
     * @throws AppTargetExistsException   if the customer already have coupon of this type.
     * @throws AppTargetNotFoundException if the customer or coupon dont exist in the database.
     */
    void purchaseCoupon(long couponId , long customerId) throws AppTargetExistsException, AppTargetNotFoundException;

    /**
     * gets all coupons from the database.
     *
     * @return all of the coupons.
     */
    Set <Coupon> getAllCoupons ();

    /**
     * gets all the coupons the customer owns.
     *
     * @param customerId the id of the customer to find the coupons of.
     * @return set of all coupons the customer owns.
     * @throws AppTargetNotFoundException if the customer dont exist in the database.
     */
    Set<Coupon> getCustomerCoupons(long customerId) throws AppTargetNotFoundException;

    /**
     * gets all the coupons of the customer by category.
     *
     * @param customerId the id of the customer to fidn the coupons of.
     * @param category   the category to filter by.
     * @return set of all coupons the customer owns by given condition.
     * @throws AppTargetNotFoundException if customer doesnt exist in the database.
     */
    Set<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws AppTargetNotFoundException;

    /**
     * gets all the coupons of the customer by max price.
     *
     * @param customerId the id of the customer to find the coupons of.
     * @param maxPrice   the max price to filter by.
     * @return set of all coupons the customer owns by given condition.
     * @throws AppTargetNotFoundException if customer doesnt exist in the database.
     */
    Set<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws AppTargetNotFoundException, AppInvalidInputException;

    /**
     * retrieves a customer from the database.
     *
     * @param customerId the id of the customer to retrieve.
     * @return the customer found in the database.
     * @throws AppTargetNotFoundException if customer id doesnt exist in the database.
     */
    Customer getCustomerDetails(long customerId) throws AppTargetNotFoundException;

    Coupon validateCoupon(long couponId , long customerId) throws AppTargetExistsException, AppTargetNotFoundException;


}
