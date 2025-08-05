package com.lego.backend.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private String userId;
    private String username;
    private String email;
    private String password;
    private String role;
}
