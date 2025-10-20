package com.hackathon.finservice.data.mapper;

import com.hackathon.finservice.data.entity.Account;
import com.hackathon.finservice.data.entity.User;
import com.hackathon.finservice.dto.user.UserRequest;
import com.hackathon.finservice.dto.user.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
            @Mapping(target = "accounts", ignore = true)
    })
    User toEntity(UserRequest userRequest);

    @Mappings({
            @Mapping(target = "accountNumber", source = "account.accountNumber"),
            @Mapping(target = "accountType", source = "account.accountType")
    })
    UserResponse toResponse(User user, Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
            @Mapping(target = "accounts", ignore = true)
    })
    void updateUserFromRequest(UserRequest userRequest, @MappingTarget User user);
}
