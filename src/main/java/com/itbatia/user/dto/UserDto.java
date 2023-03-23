package com.itbatia.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.user.model.*;
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
                .role(user.getRole().name())
                .userStatus(user.getUserStatus().name())
                .build();
    }

    public User toUser() {

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setBalance(balance);
        user.setRole(Role.valueOf(role));
        user.setUserStatus(UserStatus.valueOf(userStatus));

        return user;
    }

    public static List<UserDto> fromUsers(List<User> users) {
        return users.stream().map(UserDto::fromUser).collect(Collectors.toList());
    }
}
