package ru.Tkachenko.cryptwalletappdemo.dto;

import ru.Tkachenko.cryptwalletappdemo.entity.Wallet;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class WithdrawDto {
    @Schema(example = "rub")
    private Wallet walletFrom;
    @Schema(example = "credit card numbers",accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String walletTo;
    @Schema(example = "1234")
    private Double amountFrom;

    public Wallet getWalletFrom() {
        return walletFrom;
    }

    public void setWalletFrom(Wallet walletFrom) {
        this.walletFrom = walletFrom;
    }

    public String getWalletTo() {
        return walletTo;
    }

    public void setWalletTo(String walletTo) {
        this.walletTo = walletTo;
    }

    public Double getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Double amountFrom) {
        this.amountFrom = amountFrom;
    }
}
