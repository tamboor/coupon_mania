package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
//todo: change to required args c'tor
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    protected CompanyRepo companyRepo;
    //todo: delete if redundant.
    @Autowired
    protected CustomerRepo customerRepo;
    @Autowired
    protected CouponRepo couponRepo;
    //todo: delete if redundant.
    @Autowired
    protected PurchaseRepo purchaseRepo;

    @Override
    public Company checkCredentials(String email, String password, ClientType clientType) throws AppUnauthorizedRequestException {

        Optional<Company> companyOptional = companyRepo.findByEmailAndPassword(email, password);
        if (companyOptional.isEmpty() || !clientType.equals(ClientType.COMPANY)) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return companyOptional.get();
    }

    @Override
    public void addCoupon(Coupon coupon) throws AppTargetExistsException {
        if (couponRepo.existsByCompanyAndTitle(coupon.getCompany(), coupon.getTitle())) {
            throw new AppTargetExistsException(AppTargetExistsMessage.COUPON_EXISTS);
        }
        couponRepo.save(coupon);
    }

    //TODO: check if company verification can be implemented in REST
    //TODO: change to custom exception
    @Override
    public void updateCoupon(Coupon coupon) throws AppInvalidInputException {
        if (couponRepo.getById(coupon.getId()).getCompany() != coupon.getCompany()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.UNMATCHING_COUPON);
        }
        couponRepo.save(coupon);
    }

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

    @Override
    public Set<Coupon> getAllCompanyCoupons(long companyId) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return couponRepo.findByCompany(companyRepo.getById(companyId));
    }

    @Override
    public Set<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return couponRepo.findByCompanyAndCategory(companyRepo.getById(companyId), category);
    }

    @Override
    public Set<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws AppTargetNotFoundException, AppInvalidInputException {
        if (!companyRepo.existsById(companyId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        if (maxPrice <= 0){
            throw new AppInvalidInputException(AppInvalidInputMessage.NEGATIVE_PRICE);
        }
        return couponRepo.findByCompanyAndPrice(companyRepo.getById(companyId), maxPrice);
    }

    @Override
    public Company getCompanyDetails(long companyId) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyId)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);        }
        return companyRepo.getById(companyId);
    }
}
