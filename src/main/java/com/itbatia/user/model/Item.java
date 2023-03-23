package com.itbatia.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount; //in money

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Characteristic> characteristics = new ArrayList<>();

    @Column(name = "grade")
    private Integer grade;

    @ManyToMany(mappedBy = "items")
    private List<Order> orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ItemStatus itemStatus;
}
