package com.lsm.ws.user.context.info.dto;

import com.lsm.ws.user.domain.user.User;

import java.util.Date;

public class UserInfoDto {

    public String id;
    public String email;
    public String name;
    public String surname;
    public Date dateOfBirth;

    public static UserInfoDto from(User user) {
        UserInfoDto dto = new UserInfoDto();
        dto.id = user.id();
        dto.email = user.email();
        dto.name = user.name();
        dto.surname = user.surname();
        dto.dateOfBirth = user.dateOfBirth();

        return dto;
    }
}
