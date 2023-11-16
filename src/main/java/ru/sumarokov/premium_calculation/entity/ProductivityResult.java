package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productivity_result")
public class ProductivityResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_level", nullable = false)
    private ProductivityLevel generalLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sum_amount_credits_level", nullable = false)
    private ProductivityLevel sumAmountCreditsLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "count_credits_level", nullable = false)
    private ProductivityLevel countCreditsLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_penetration_level", nullable = false)
    private ProductivityLevel insurancePenetrationLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sms_penetration_level", nullable = false)
    private ProductivityLevel smsPenetrationLevel;
    @OneToOne()
    @MapsId
    @JoinColumn(name = "users_id")
    private User user;

    public ProductivityResult() {
    }

    public ProductivityResult(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductivityLevel getGeneralLevel() {
        return generalLevel;
    }

    public void setGeneralLevel(ProductivityLevel generalLevel) {
        this.generalLevel = generalLevel;
    }

    public ProductivityLevel getSumAmountCreditsLevel() {
        return sumAmountCreditsLevel;
    }

    public void setSumAmountCreditsLevel(ProductivityLevel sumAmountCreditsLevel) {
        this.sumAmountCreditsLevel = sumAmountCreditsLevel;
    }

    public ProductivityLevel getCountCreditsLevel() {
        return countCreditsLevel;
    }

    public void setCountCreditsLevel(ProductivityLevel countCreditsLevel) {
        this.countCreditsLevel = countCreditsLevel;
    }

    public ProductivityLevel getInsurancePenetrationLevel() {
        return insurancePenetrationLevel;
    }

    public void setInsurancePenetrationLevel(ProductivityLevel insurancePenetrationLevel) {
        this.insurancePenetrationLevel = insurancePenetrationLevel;
    }

    public ProductivityLevel getSmsPenetrationLevel() {
        return smsPenetrationLevel;
    }

    public void setSmsPenetrationLevel(ProductivityLevel smsPenetrationLevel) {
        this.smsPenetrationLevel = smsPenetrationLevel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
