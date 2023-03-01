package ru.Tkachenko.cryptwalletappdemo.dto;

import ru.Tkachenko.cryptwalletappdemo.entity.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;

public class DepositDto {
    @Schema(example = "btc")
    private Wallet walletTo;
    @Schema(example = "0.001")
    private Double amountTo;

    public Wallet getWalletTo() {
        return walletTo;
    }

    public void setWalletTo(Wallet walletTo) {
        this.walletTo = walletTo;
    }

    public Double getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Double amountTo) {
        this.amountTo = amountTo;
    }
}
