package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;

import java.sql.DataTruncation;
import java.util.Set;

public interface CompanyService {
    /**
     * Checks if given credentials match company login credentials.
     * @param email user email.
     * @param password user password.
     * @param clientType the type of the client.
     * @return returns found company if credentials match a company in the database.
     * @throws AppUnauthorizedRequestException if credentials dont match any company in the database.
     */
    Company checkCredentials(String email, String password, ClientType clientType) throws AppUnauthorizedRequestException;

    /**
     * adds a coupon to the database.
     * @param coupon, the coupon to add.
     * @throws AppTargetExistsException if coupon with matching name and company exists in the database.
     */
    void addCoupon(Coupon coupon) throws AppTargetExistsException, AppInvalidInputException;

    /**
     * updates a coupon in the database.
     * @param coupon the coupon to update.
     * @throws AppInvalidInputException if coupon is of other company.
     */
    void updateCoupon(Coupon coupon) throws Exception;

    /**
     *  deletes a coupon from the database.
     * @param couponId coupon to delete
     * @param companyId company that wishes to delete the coupon
     * @throws AppTargetNotFoundException if the coupon was not found
     * @throws AppInvalidInputException if the coupon is not of given company
     */
    void deleteCoupon(long couponId, long companyId) throws AppTargetNotFoundException, AppInvalidInputException;

    /**
     * retrieves a company by id.
     * @param couponId company to retrieve.
     * @return the coupon found.
     * @throws AppTargetNotFoundException coupon was not found.
     */
    Coupon getCouponByID(long couponId) throws AppTargetNotFoundException;

    /**
     * gets all coupons of given company.
     * @param companyId the company to find the coupons of.
     * @return the set of all coupons.
     * @throws AppTargetNotFoundException if company dont exist in the database.
     */
    Set<Coupon> getAllCompanyCoupons(long companyId) throws AppTargetNotFoundException;

    /**
     * gets all coupons of given company and category.
     * @param companyId the company to find the coupons of.
     * @param category the category to filter by.
     * @return the set of all coupons.
     * @throws AppTargetNotFoundException if company dont exist in the database.
     */
    Set<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws AppTargetNotFoundException;

    /**
     * gets all coupons of given company and max price.
     * @param companyId the company to find the coupons of.
     * @param maxPrice the max price to filter by.
     * @return the set of all coupons.
     * @throws AppTargetNotFoundException if company dont exist in the database or the max price given is a negative number.
     */
    Set<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws AppTargetNotFoundException, AppInvalidInputException;

    /**
     * retrieves a company from the database.
     * @param companyId the id of the company to find.
     * @return the company found.
     * @throws AppTargetNotFoundException if company was not found in the database..
     */
    Company getCompanyDetails(long companyId) throws AppTargetNotFoundException;


}
