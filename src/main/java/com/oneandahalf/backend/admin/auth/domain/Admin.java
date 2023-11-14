package com.oneandahalf.backend.admin.auth.domain;

import com.oneandahalf.backend.admin.auth.exception.MismatchAdminPasswordException;
import com.oneandahalf.backend.common.domain.CommonDomainModel;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Admin extends CommonDomainModel {

    private String username;
    private String password;

    public void login(String password) {
        if (this.password.equals(password)) {
            return;
        }
        throw new MismatchAdminPasswordException();
    }
}
