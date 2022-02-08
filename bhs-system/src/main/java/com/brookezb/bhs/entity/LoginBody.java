package com.brookezb.bhs.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author brooke_zb
 */
@Data
public class LoginBody {
    @NotNull
    private String username;

    @NotNull
    private String password;

    private boolean rememberMe = false;

    public String toString() {
        return "LoginBody(username=" + this.getUsername() + ", password=******" + ", rememberMe=" + this.isRememberMe() + ")";
    }
}
