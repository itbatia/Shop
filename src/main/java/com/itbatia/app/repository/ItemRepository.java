package com.itbatia.app.repository;

import com.itbatia.app.model.Item;
import com.itbatia.app.model.OrganizationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM User u " +
            "LEFT JOIN Organization o ON u.id = o.owner.id " +
            "LEFT JOIN Item i ON o.id = i.organization.id " +
            "WHERE o.organizationStatus = :status AND u.id = :userId")
    List<Item> findItemsOnlyFromActiveOrganizations(Long userId, OrganizationStatus status);
}
