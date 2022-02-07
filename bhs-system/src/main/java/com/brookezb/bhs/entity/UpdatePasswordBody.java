package com.brookezb.bhs.entity;

import lombok.Data;

/**
 * @author brooke_zb
 */
@Data
public class UpdatePasswordBody {
    private String oldPassword;
    private String newPassword;
}
