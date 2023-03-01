package ru.Tkachenko.cryptwalletappdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="currency_rates")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Enumerated(value=EnumType.STRING)
    @Column(name = "convert_from")
    private Wallet convertFrom;
    @Enumerated(value=EnumType.STRING)
    @Column(name="convert_to")
    private Wallet convertTo;
    @Column(name="convert_ratio")
    private Double ratio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getConvertFrom() {
        return convertFrom;
    }

    public void setConvertFrom(Wallet convertFrom) {
        this.convertFrom = convertFrom;
    }

    public Wallet getConvertTo() {
        return convertTo;
    }

    public void setConvertTo(Wallet convertTo) {
        this.convertTo = convertTo;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
}
