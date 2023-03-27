package com.itbatia.app.service;

import com.itbatia.app.exceptions.OrganizationNotFoundException;
import com.itbatia.app.model.*;
import com.itbatia.app.repository.OrganizationRepository;
import com.itbatia.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    @Value("${shop.commission.percentage}")
    private Integer commissionPercentage;

    @Transactional
    public Organization createOrganization(Organization organization, Principal principal) {

        User currentUser = userRepository.findByUsername(principal.getName()).orElseThrow();
        if (currentUser.getRole().equals(Role.ROLE_USER))
            organization.setOwner(currentUser);

        Organization createdOrganization = organizationRepository.save(organization);
        log.info("IN createOrganization - Organization with id={} successfully created!", createdOrganization.getId());
        return createdOrganization;
    }

    public Organization findById(long id) {
        return organizationRepository.findById(id).orElseThrow(() -> {
            throw new OrganizationNotFoundException("Organization with id=" + id + " not found");
        });
    }

    /**
     * @param mathSignOfOperation Takes 2 values. If the item is purchased: '+', if the item is returned: '-'.
     */
    @Transactional
    public void updateBalanceOfOrganization(Item item, MathSignOfOperation mathSign) {
        BigDecimal profitFromTheSaleItems = getProfitFromTheSaleItem(item);
        Long organizationId = item.getOrganization().getId();
        updateBalance(organizationId, profitFromTheSaleItems, mathSign);
        log.info("IN updateBalanceOfOrganization - Balance of organization with id={} successfully updated!", organizationId);
    }

    /**
     * <p color="blue">BigDecimal storeIncome - is the store's profit from the sale of items.
     * It is not used anywhere else, yet.</p>
     *
     * @return Income of the organization from the sale of items
     */
    private BigDecimal getProfitFromTheSaleItem(Item item) {
        BigDecimal percentOfCommission = BigDecimal.valueOf(commissionPercentage);

        Integer itemsAmountInOrder = item.getTotalAmount();
        BigDecimal totalPriceOfItemsInOrder = item.getPrice().multiply(BigDecimal.valueOf(itemsAmountInOrder));

        BigDecimal storeIncome = totalPriceOfItemsInOrder.multiply(percentOfCommission);
        return totalPriceOfItemsInOrder.subtract(storeIncome);
    }

    private void updateBalance(Long organizationId, BigDecimal countOfMoney, MathSignOfOperation mathSign) {
        Organization organization = findById(organizationId);

        if (mathSign.equals(MathSignOfOperation.MINUS))
            organization.setBalance(organization.getBalance().subtract(countOfMoney));
        if (mathSign.equals(MathSignOfOperation.MINUS))
            organization.setBalance(organization.getBalance().add(countOfMoney));
    }

    @Transactional
    public void updateOrganization(Organization organization) {
        organizationRepository.save(organization);
        log.info("IN updateOrganization - Organization with id={} successfully updated!", organization.getId());
    }
}
