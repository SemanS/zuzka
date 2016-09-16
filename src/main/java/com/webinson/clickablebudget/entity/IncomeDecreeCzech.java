package com.webinson.clickablebudget.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Slavo on 14.09.2016.
 */
@Entity
@Table(name = "decree_income_czech", schema = "clickable_budget")
@Data
@NoArgsConstructor
public class IncomeDecreeCzech implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "klass")
    private String klass;

}
