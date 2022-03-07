package com.couponmania2.coupon_project.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDetails {
    private int id;
    private String userName;
    private String userPass;
    private ClientType clientType;
}
