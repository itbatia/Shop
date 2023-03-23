package com.itbatia.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.user.model.Organization;
import com.itbatia.user.model.OrganizationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class OrganizationDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("logo_url")
    private String logoUrl;
    @JsonProperty("owner")
    private UserDto owner;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("status")
    private String organizationStatus;

    public static OrganizationDto fromOrganization(Organization organization) {

        return OrganizationDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .logoUrl(organization.getLogoUrl())
                .owner(UserDto.fromUser(organization.getOwner()))
                .balance(organization.getBalance())
                .organizationStatus(organization.getOrganizationStatus().name())
                .build();
    }

    public Organization toOrganization() {

        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(name);
        organization.setDescription(description);
        organization.setLogoUrl(logoUrl);
        organization.setOwner(owner != null ? owner.toUser() : null);
        organization.setBalance(balance != null ? balance : new BigDecimal(0));
        organization.setOrganizationStatus(organizationStatus != null ?
                OrganizationStatus.valueOf(organizationStatus) : OrganizationStatus.NEW);

        return organization;
    }

    public static List<OrganizationDto> fromOrganizations(List<Organization> organizations) {
        return organizations.stream().map(OrganizationDto::fromOrganization).collect(Collectors.toList());
    }
}
