package com.pos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "bar_codes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BarCode extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bar_code_id")
    private Long barCodeId;
    
    @NotBlank(message = "Bar code is required")
    @Column(name = "bar_code", unique = true, nullable = false)
    private String barCode;
    
    @OneToMany(mappedBy = "barCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}