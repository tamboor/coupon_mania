package com.couponmania2.coupon_project.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean checkNullFields() {
        if (this.userName == null || this.userPass == null || this.getRole() == null) {
            return true;
        }
        return false;
    }

    public boolean roleCheck() {
        List<String> allRoles = Arrays.stream(ClientType.values()).map(r -> r.getName()).collect(Collectors.toList());
        for (String name : allRoles){
            if (this.role.equals(name)){
                return true;
            }
        }
        return false;

    }
}


