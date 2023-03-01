package ru.Tkachenko.cryptwalletappdemo.dto;

import ru.Tkachenko.cryptwalletappdemo.entity.Wallet;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class ConvertDto {
    @Schema(description = "Конвертировать из", example = "rub")
    private Wallet walletFrom;
    @Schema(description = "Сконвертировать в", example = "btc")
    private Wallet walletTo;
    @Schema(description = "Было", example = "1000")
    private Double amountFrom;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Стало", example = "0.45",accessMode = Schema.AccessMode.READ_ONLY)
    private Double amountTo;

    public Wallet getWalletFrom() {
        return walletFrom;
    }

    public void setWalletFrom(Wallet walletFrom) {
        this.walletFrom = walletFrom;
    }

    public Wallet getWalletTo() {
        return walletTo;
    }

    public void setWalletTo(Wallet walletTo) {
        this.walletTo = walletTo;
    }

    public Double getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Double amountFrom) {
        this.amountFrom = amountFrom;
    }

    public Double getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Double amountTo) {
        this.amountTo = amountTo;
    }
}

