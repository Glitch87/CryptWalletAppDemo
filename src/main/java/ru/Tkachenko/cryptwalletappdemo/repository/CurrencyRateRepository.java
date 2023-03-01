package ru.Tkachenko.cryptwalletappdemo.repository;

import ru.Tkachenko.cryptwalletappdemo.entity.CurrencyRate;
import ru.Tkachenko.cryptwalletappdemo.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    CurrencyRate findByConvertFromAndConvertTo(Wallet convertFrom, Wallet convertTo);
    List<CurrencyRate> findAllByConvertFrom(Wallet convertFrom);
    long count();
}
