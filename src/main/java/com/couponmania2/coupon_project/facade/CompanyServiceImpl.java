package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
//todo: change to required args c'tor
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CustomerRepo customerRepo;
    @Autowired
    protected CouponRepo couponRepo;
    @Autowired
    protected PurchaseRepo purchaseRepo;

    @Override
    public void addCoupon(Coupon coupon) {
        if (couponRepo.existsByCompanyAndTitle(coupon.getCompany(), coupon.getTitle())) {
            //todo: throw add custom  exp
        }
        couponRepo.save(coupon);
    }

    //todo: check if company verification can be implemented in REST
    //todo: change to custom exception
    @Override
    public void updateCoupon(Coupon coupon) throws Exception {
        if (couponRepo.getById(coupon.getId()).getCompany() != coupon.getCompany()) {
            throw new Exception("CCCCCCCCC");
        }
        couponRepo.save(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) {
        if (!couponRepo.existsById(couponId)) {
            //:todo throw exp coupon exist
        }
        couponRepo.deleteById(couponId);


    }

    @Override
    public Set<Coupon> getAllCompanyCoupons(int companyId) {
        if (!companyRepo.existsById(companyId)) {
            //todo: throw custom exception
        }
        return couponRepo.findByCompany(companyRepo.getById(companyId));
    }

    @Override
    public Set<Coupon> getCompanyCouponsByCategory(int companyId, Category category) {
        if (!companyRepo.existsById(companyId)) {
            //todo: throw exp id is not exist
        }
        return couponRepo.findByCompanyAndCategory(companyRepo.getById(companyId), category);
    }

    @Override
    public Set<Coupon> getCompanyCouponsByMaxPrice(int companyId, double maxPrice) {
        if (!companyRepo.existsById(companyId) || maxPrice <= 0) {
            //todo: throw exp id is not exist
        }
        return couponRepo.findByCompanyAndPrice(companyRepo.getById(companyId), maxPrice);
    }

    @Override
    public Company getCompanyDetails(int companyId) {
        if (!companyRepo.existsById(companyId)) {
            //todo: throw custom exeption
        }
        return companyRepo.getById(companyId);
    }
}
