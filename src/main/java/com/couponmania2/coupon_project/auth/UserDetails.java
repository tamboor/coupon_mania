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

    /**
     * Checks if the object has any null fields.
     *
     * @return true if has null fields, false if non of the fields are null.
     */
    public boolean checkNullFields() {
        return this.userName == null || this.userPass == null || this.getRole() == null;
    }

    /**
     * Checks if the user's role is valid (is one of the roles in the clienttype enum)
     *
     * @return true if role is valid, false if not.
     */
    public boolean roleCheck() {
        List<String> allClientTypes = Arrays.stream(ClientType.values())
                .map(r -> r.getName())
                .collect(Collectors.toList());
        for (String name : allClientTypes) {
            if (this.role.equals(name)) {
                return true;
            }
        }
        return false;

    }
}


