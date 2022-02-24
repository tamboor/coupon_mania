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
        if (!couponRepo.existsByCompanyAndTitle(coupon.getCompany(), coupon.getTitle())) {
            couponRepo.save(coupon);
        } else {
            //todo: throw add custom  exp
        }
    }

    //todo: check if company verification can be implemented in REST
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
    public Set<Coupon> getCompanyByCategory(Category category) {
        return null;
    }

    @Override
    public Set<Coupon> getCompanyByMaxPrice(double maxPrice) {
        return null;
    }

    @Override
    public Company getCompanyDetails(int companyId) {
        if (!companyRepo.existsById(companyId)){
            //todo: throw custom exeption
        }
        return companyRepo.getById(companyId);
    }
}
