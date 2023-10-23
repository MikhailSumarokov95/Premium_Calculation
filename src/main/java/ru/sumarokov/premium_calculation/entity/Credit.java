package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import ru.sumarokov.premium_calculation.helper.CreditCategory;
import ru.sumarokov.premium_calculation.helper.InsuranceCategory;

@Entity
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CreditCategory category;
    private Double total;
    private Integer term;
    private Float rate;
    @Enumerated(EnumType.STRING)
    private InsuranceCategory insurance;
    private Boolean isConnectedSms;
    private Boolean isConsultantAvailability;
    private Boolean isUsedSelfReject;

    public Credit() {
    }

    public Credit(CreditCategory category, Double sum, Integer term, Float rate, InsuranceCategory insurance,
                  Boolean isConnectedSms, Boolean isConsultantAvailability, Boolean isUsedSelfReject) {
        this.category = category;
        this.total = sum;
        this.term = term;
        this.rate = rate;
        this.insurance = insurance;
        this.isConnectedSms = isConnectedSms;
        this.isConsultantAvailability = isConsultantAvailability;
        this.isUsedSelfReject = isUsedSelfReject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCategory getCategory() {
        return category;
    }

    public void setCategory(CreditCategory category) {
        this.category = category;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public InsuranceCategory getInsurance() {
        return insurance;
    }

    public void setInsurance(InsuranceCategory insurance) {
        this.insurance = insurance;
    }

    public Boolean getIsConnectedSms() {
        return isConnectedSms;
    }

    public void setIsConnectedSms(Boolean connectedSMS) {
        isConnectedSms = connectedSMS;
    }

    public Boolean getIsConsultantAvailability() {
        return isConsultantAvailability;
    }

    public void setIsConsultantAvailability(Boolean consultantAvailability) {
        isConsultantAvailability = consultantAvailability;
    }

    public Boolean getIsUsedSelfReject() {
        return isUsedSelfReject;
    }

    public void setIsUsedSelfReject(Boolean usedSelfReject) {
        isUsedSelfReject = usedSelfReject;
    }
}
