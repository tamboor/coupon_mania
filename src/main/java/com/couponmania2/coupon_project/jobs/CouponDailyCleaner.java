package com.couponmania2.coupon_project.jobs;

import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class CouponDailyCleaner {

    @Autowired
    CouponRepo couponRepo;

    /**
     * Daily job that checks once a day for expired coupons and deletes them.
     */
    @Scheduled(fixedRate = 1000*60*60*24)
    public void cleanExpiredCoupons(){
        //TODO: Check if need handling interuptions
        couponRepo.deleteByEndDateBefore(DateUtils.getCurrDate());
    }
}
