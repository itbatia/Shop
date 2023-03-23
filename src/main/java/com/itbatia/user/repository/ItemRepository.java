package com.itbatia.user.repository;

import com.itbatia.user.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.characteristics LEFT JOIN FETCH i.tags WHERE i.id=?1")
    Optional<Item> findById(Long id);
}
