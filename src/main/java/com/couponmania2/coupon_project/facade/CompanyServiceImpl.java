package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
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
    public Company findByLoginCredentials(String email, String password, ClientType clientType) {

        Optional<Company> companyOptional = companyRepo.findByEmailAndPassword(email , password);
        if (companyOptional.isEmpty() || !clientType.equals(ClientType.COMPANY)){
            //TODO: throw not found exception
        }
        return companyOptional.get();
    }

    @Override
    public void addCoupon(Coupon coupon) {
        if (couponRepo.existsByCompanyAndTitle(coupon.getCompany(), coupon.getTitle())) {
            //TODO: throw add custom  exp
        }
        couponRepo.save(coupon);
    }

    //TODO: check if company verification can be implemented in REST
    //TODO: change to custom exception
    @Override
    public void updateCoupon(Coupon coupon)  {
        if (couponRepo.getById(coupon.getId()).getCompany() != coupon.getCompany()) {
            //TODO:throw exception
        }
        couponRepo.save(coupon);
    }

    @Override
    public void deleteCoupon(long couponId , long companyId) {
        Optional<Coupon> couponOptional = couponRepo.findById(couponId);
        if (couponOptional.isEmpty()) {
            //TODO: throw not found
        }
        Coupon coupon = couponOptional.get();
        if (coupon.getId() != companyId){
            //TODO: throw unauthorized
        }

        couponRepo.deleteById(couponId);
    }

    @Override
    public Set<Coupon> getAllCompanyCoupons(long companyId) {
        if (!companyRepo.existsById(companyId)) {
            //TODO: throw custom exception
        }
        return couponRepo.findByCompany(companyRepo.getById(companyId));
    }

    @Override
    public Set<Coupon> getCompanyCouponsByCategory(long companyId, Category category) {
        if (!companyRepo.existsById(companyId)) {
            //TODO: throw exp id is not exist
        }
        return couponRepo.findByCompanyAndCategory(companyRepo.getById(companyId), category);
    }

    @Override
    public Set<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) {
        if (!companyRepo.existsById(companyId) || maxPrice <= 0) {
            //TODO: throw exp id is not exist
        }
        return couponRepo.findByCompanyAndPrice(companyRepo.getById(companyId), maxPrice);
    }

    @Override
    public Company getCompanyDetails(long companyId) {
        if (!companyRepo.existsById(companyId)) {
            //TODO: throw custom exeption
        }
        return companyRepo.getById(companyId);
    }
}
