package com.brookezb.bhs.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author brooke_zb
 */
@Data
public class UpdatePasswordBody {
    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;

    public String toString() {
        return "UpdatePasswordBody(oldPassword=******, newPassword=******)";
    }
}
