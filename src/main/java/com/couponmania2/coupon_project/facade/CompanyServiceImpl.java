package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;
    private final CouponRepo couponRepo;

    /**
     * Checks if given credentials match company login credentials.
     * @param email user email.
     * @param password user password.
     * @param clientType the type of the client.
     * @return returns found company if credentials match a company in the database.
     * @throws AppUnauthorizedRequestException if credentials dont match any company in the database.
     */
    @Override
    public Company checkCredentials(String email, String password, ClientType clientType) throws AppUnauthorizedRequestException {

        Optional<Company> companyOptional = companyRepo.findByEmailAndPassword(email, password);
        if (companyOptional.isEmpty() || !clientType.equals(ClientType.company)) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return companyOptional.get();
    }

    /**
     * adds a coupon to the database.
     * @param coupon, the coupon to add.
     * @throws AppTargetExistsException if coupon with matching name and company exists in the database.
     */
    @Override
    public void addCoupon(Coupon coupon) throws AppTargetExistsException, AppInvalidInputException {
        if (couponRepo.existsByCompanyAndTitle(coupon.getCompany(), coupon.getTitle())) {
            throw new AppTargetExistsException(AppTargetExistsMessage.COUPON_EXISTS);
        }
        if(coupon.getPrice()<0)
        {
            throw new AppInvalidInputException(AppInvalidInputMessage.NEGATIVE_PRICE);
        }
        if(coupon.getAmount()<0)
        {
            throw new AppInvalidInputException(AppInvalidInputMessage.NEGATIVE_AMOUNT);
        }
        if(coupon.getEndDate().before(coupon.getStartDate()))
        {
            throw new AppInvalidInputException(AppInvalidInputMessage.END_DATE_BEFORE_START_DATE);
        }
        if(coupon.getEndDate().before(DateUtils.getCurrDate()))
        {
            throw new AppInvalidInputException(AppInvalidInputMessage.END_DATE_BEFORE_CURRENT_DATE);
        }
        couponRepo.save(coupon);

    }

    /**
     * updates a coupon in the database.
     * @param coupon the coupon to update.
     * @throws AppInvalidInputException if coupon is of other company.
     */
    @Override
    public void updateCoupon(Coupon coupon) throws AppInvalidInputException {
        if (couponRepo.getById(coupon.getId()).getCompany() != coupon.getCompany()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.UNMATCHING_COUPON);
        }
        couponRepo.save(coupon);
    }

    /**
     *  deletes a coupon from the database.
     * @param couponId coupon to delete
     * @param companyId company that wishes to delete the coupon
     * @throws AppTargetNotFoundException if the coupon was not found
     * @throws AppInvalidInputException if the coupon is not of given company
     */
    @Override
    public void deleteCoupon(long couponId, long companyId) throws AppTargetNotFoundException, AppInvalidInputException {
        Optional<Coupon> couponOptional = couponRepo.findById(couponId);
        if (couponOptional.isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COUPON_NOT_FOUND);
        }
        Coupon coupon = couponOptional.get();
        if (coupon.getCompany().getId() != companyId) {
            throw new AppInvalidInputException(AppInvalidInputMessage.UNMATCHING_COUPON);
        }

        couponRepo.deleteById(couponId);
    }

    /**
     * retrieves a company by id.
     * @param couponId company to retrieve.
     * @return the coupon found.
     * @throws AppTargetNotFoundException coupon was not found.
     */
    @Override
    public Coupon getCouponByID(long couponId) throws AppTargetNotFoundException {
        if (!couponRepo.existsById(couponId)){
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COUPON_NOT_FOUND);
        }
        Optional<Coupon> optionalCoupon = couponRepo.findById(couponId);
        return optionalCoupon.get();
    }

    /**
     * gets all coupons of given company.
     * @param companyId the company to find the coupons of.
     * @return the set of all coupons.
     * @throws AppTargetNotFoundException if company dont exist in the database.
     */
    @Override
    public Set<Coupon> getAllCompanyCoupons(long companyId) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return couponRepo.findByCompany(companyRepo.getById(companyId));
    }

    /**
     * gets all coupons of given company and category.
     * @param companyId the company to find the coupons of.
     * @param category the category to filter by.
     * @return the set of all coupons.
     * @throws AppTargetNotFoundException if company dont exist in the database.
     */
    @Override
    public Set<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return couponRepo.findByCompanyAndCategory(companyRepo.getById(companyId), category);
    }

    /**
     * gets all coupons of given company and max price.
     * @param companyId the company to find the coupons of.
     * @param maxPrice the max price to filter by.
     * @return the set of all coupons.
     * @throws AppTargetNotFoundException if company dont exist in the database or the max price given is a negative number.
     */
    @Override
    public Set<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws AppTargetNotFoundException, AppInvalidInputException {
        if (companyRepo.findById(companyId).isEmpty() || !companyRepo.existsById(companyId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        if (maxPrice <= 0) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NEGATIVE_PRICE);
        }
        return couponRepo.findByCompanyAndPriceLessThanEqual(companyRepo.getById(companyId), maxPrice);
    }

    /**
     * retrieves a company from the database.
     * @param companyId the id of the company to find.
     * @return the company found.
     * @throws AppTargetNotFoundException if company was not found in the database..
     */
    @Override
    public Company getCompanyDetails(long companyId) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyId)|| companyRepo.findById(companyId).isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return companyRepo.getById(companyId);
    }
}
