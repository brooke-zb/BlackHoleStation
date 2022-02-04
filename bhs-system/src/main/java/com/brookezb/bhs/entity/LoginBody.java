package com.brookezb.bhs.entity;

import lombok.Data;

/**
 * @author brooke_zb
 */
@Data
public class LoginBody {
    private String username;
    private String password;
    private boolean rememberMe = false;
}
