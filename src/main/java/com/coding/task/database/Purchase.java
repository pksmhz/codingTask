package com.coding.task.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "PURCHASES")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPERATION_ID", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OPERATION_ID", nullable = false)
    @ToString.Exclude
    private Customer customers;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "AMOUNT", nullable = false, precision = 20, scale = 2)
    private BigDecimal amount;

    public int getMonth() {
        return date.getMonthValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Purchase purchase = (Purchase) o;
        return id != null && Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}