package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_group_id", nullable = false)
    private ProductGroup productGroup;
    //TODO: С деньгами BigDecimal
    private Double amount;
    private Integer term;
    private Double rate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id", nullable = false)
    private Insurance insurance;
    private Boolean isConnectedSms;
    private Boolean isFur;
    private Boolean isConsultantAvailability;
    private Boolean isUsedSelfReject;
    @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PreliminaryCreditResult preliminaryCreditResult;

    public Credit() {
    }

    public Credit(Long id, ProductGroup productGroup, Double amount,
                  Integer term, Double rate, Insurance insurance,
                  Boolean isConnectedSms, Boolean isFur,
                  Boolean isConsultantAvailability, Boolean isUsedSelfReject,
                  PreliminaryCreditResult preliminaryCreditResult) {
        this.id = id;
        this.productGroup = productGroup;
        this.amount = amount;
        this.term = term;
        this.rate = rate;
        this.insurance = insurance;
        this.isConnectedSms = isConnectedSms;
        this.isFur = isFur;
        this.isConsultantAvailability = isConsultantAvailability;
        this.isUsedSelfReject = isUsedSelfReject;
        this.preliminaryCreditResult = preliminaryCreditResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Boolean getIsConnectedSms() { return isConnectedSms; }

    public void setIsConnectedSms(Boolean connectedSMS) { isConnectedSms = connectedSMS; }

    public Boolean getIsFur() {
        return isFur;
    }

    public void setIsFur(Boolean fur) {
        isFur = fur;
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

    public PreliminaryCreditResult getPreliminaryCreditResult() {
        return preliminaryCreditResult;
    }

    public void setPreliminaryCreditResult(PreliminaryCreditResult preliminaryCreditResult) {
        this.preliminaryCreditResult = preliminaryCreditResult;
    }
}

