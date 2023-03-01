package ru.Tkachenko.cryptwalletappdemo.service;

import ru.Tkachenko.cryptwalletappdemo.dto.ExchangeDto;
import ru.Tkachenko.cryptwalletappdemo.entity.CurrencyRate;
import ru.Tkachenko.cryptwalletappdemo.entity.User;
import ru.Tkachenko.cryptwalletappdemo.entity.Wallet;
import ru.Tkachenko.cryptwalletappdemo.repository.CurrencyRateRepository;
import ru.Tkachenko.cryptwalletappdemo.repository.TransactionLogRepository;
import ru.Tkachenko.cryptwalletappdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;
    @Autowired
    private TransactionLogRepository transactionLogRepository;
    public ExchangeDto newExchangeRate(ExchangeDto exchangeDto) {
        if(currencyRateRepository.count()==0)
            createCurrencyRateTable();
        for (Map.Entry<Wallet, Double> entry: exchangeDto.getWalletRates().entrySet()) {
            if (entry.getValue()<0.0)
                throw new RuntimeException();
            setNewRatio(exchangeDto.getBaseCurrency().toString()
                    ,entry.getKey().toString(),entry.getValue());
            setNewRatio(entry.getKey().toString()
                    ,exchangeDto.getBaseCurrency().toString(),1/entry.getValue());
        }
        return exchangeDto;
    }
    public Map.Entry<String, String> walletSum (String walletType) {
        if (Wallet.valueOf(walletType).equals(null))
            throw new RuntimeException();
        List<User> users = userRepository
                .findUserByWalletType(walletType);
        Double sum=0.0;
        for(User entry: users) {
            sum+= entry.getUserWallet(walletType);
        }
        return Map.entry(walletType,sum.toString());
    }
    public Map.Entry<String, String> transactionsCount (Date dateFrom, Date dateTo) {
        if (dateTo.equals(null))
            dateTo=new Date(System.currentTimeMillis());
        if (dateFrom.equals(null))
            dateFrom = transactionLogRepository.findTopByOrderByDate().getDate();
        Long count = transactionLogRepository.countByDateBetween(dateFrom,dateTo);
        return Map.entry("transaction_count",count.toString());
    }
    private void createCurrencyRateTable() {
        for(Wallet walletFrom: Wallet.values()) {
            for(Wallet walletTo: Wallet.values()) {
                if (walletTo.equals(walletFrom))
                    continue;
                CurrencyRate currencyRate=new CurrencyRate();
                currencyRate.setConvertFrom(walletFrom);
                currencyRate.setConvertTo(walletTo);
                currencyRate.setRatio(1.0);
                currencyRateRepository.save(currencyRate);

            }
        }
        currencyRateRepository.flush();
    }
    private void setNewRatio(String walletFrom, String walletTo,
                             Double ratio) {
        CurrencyRate currencyRate= currencyRateRepository.
                findByConvertFromAndConvertTo
                (Wallet.valueOf(walletFrom),Wallet.valueOf(walletTo));
        currencyRate.setRatio(ratio);
        currencyRateRepository.save(currencyRate);
        currencyRateRepository.flush();
    }
}
