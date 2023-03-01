package ru.Tkachenko.cryptwalletappdemo.dto;

import ru.Tkachenko.cryptwalletappdemo.entity.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class ExchangeDto {
    @Schema(example = "rub",accessMode = Schema.AccessMode.WRITE_ONLY)
    private Wallet baseCurrency;
    private Map<Wallet, Double> walletRates;

    public Wallet getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Wallet baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Map<Wallet, Double> getWalletRates() {
        return walletRates;
    }

    public void setWalletRates(Map<Wallet, Double> walletRates) {
        this.walletRates = walletRates;
    }
}
