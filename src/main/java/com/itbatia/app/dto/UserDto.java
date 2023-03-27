package com.itbatia.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.app.model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class UserDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @Schema(description = "username for user`s email", example = "ivai.ivanov@gmail.com")
    @JsonProperty("email")
    private String email;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("role")
    private String role;
    @JsonProperty("status")
    private String userStatus;

    public static UserDto fromUser(User user) {

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .balance(user.getBalance())
                .role(user.getRole() != null ? user.getRole().name() : Role.ROLE_USER.name())
                .userStatus(user.getUserStatus() != null ? user.getUserStatus().name() : null)
                .build();
    }

    public User toUser() {

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setBalance(balance);
        user.setRole(role != null ? Role.valueOf(role) : null);
        user.setUserStatus(userStatus != null ? UserStatus.valueOf(userStatus) : null);

        return user;
    }

    public static List<UserDto> fromUsers(List<User> users) {
        return users.stream().map(UserDto::fromUser).collect(Collectors.toList());
    }
}
