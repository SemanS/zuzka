package com.webinson.clickablebudget.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Slavo on 13.09.2016.
 */
@Entity
@Table(name = "outcome", schema = "clickable_budget")
@Data
@NoArgsConstructor
public class Outcome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "paragraf")
    private String paragraf;

    @Column(name = "approved_budget")
    private Double approvedBudget;

    @Column(name = "adjusted_budget")
    private double adjustedbudget;

    @Column(name = "spent_budget")
    private double spentBudget;

    @Transient
    private int state;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Outcome(String paragraf, Double approvedBudget, double adjustedbudget, double spentBudget, Date date, City city) {
        this.paragraf = paragraf;
        this.approvedBudget = approvedBudget;
        this.adjustedbudget = adjustedbudget;
        this.spentBudget = spentBudget;
        this.date = date;
        this.city = city;
    }

    private int getState() {
        return (int) (this.spentBudget / this.adjustedbudget);
    }

}
