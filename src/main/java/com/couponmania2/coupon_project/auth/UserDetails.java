package com.couponmania2.coupon_project.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
/**
 * Bean for storing info needed in access/authentication operations.
 */
public class UserDetails {
    @JsonIgnore
    private long id = -1;
    private String userName;
    private String userPass;
    private String role;
}
