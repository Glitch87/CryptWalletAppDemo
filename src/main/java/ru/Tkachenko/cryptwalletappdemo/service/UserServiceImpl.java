package ru.Tkachenko.cryptwalletappdemo.service;

import ru.Tkachenko.cryptwalletappdemo.dto.ConvertDto;
import ru.Tkachenko.cryptwalletappdemo.dto.DepositDto;
import ru.Tkachenko.cryptwalletappdemo.dto.WithdrawDto;
import ru.Tkachenko.cryptwalletappdemo.entity.CurrencyRate;
import ru.Tkachenko.cryptwalletappdemo.entity.TransactionLog;
import ru.Tkachenko.cryptwalletappdemo.repository.CurrencyRateRepository;
import ru.Tkachenko.cryptwalletappdemo.repository.TransactionLogRepository;
import ru.Tkachenko.cryptwalletappdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.Tkachenko.cryptwalletappdemo.entity.User;

import java.util.Map;


@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionLogRepository transactionLogRepository;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public Map<String,String> getUserWallets() {
        User user = userRepository.findByUsername(getLoggedUser());
        Map<String,String> map= user.getMap();
        return  map;
    }
    public DepositDto deposit(DepositDto depositDto) {
        User user = userRepository.findByUsername(getLoggedUser());
        var walletTo= depositDto.getWalletTo();
        var amount = depositDto.getAmountTo();
        user.setUserWallet(walletTo.toString(),amount);
        userRepository.save(user);
        userRepository.flush();
        addTransaction(user,"Deposit "+ amount.toString()+walletTo +
                " to " + walletTo + " wallet");
        depositDto.setAmountTo(user.getUserWallet(walletTo.toString()));
        return depositDto;
    }
    public WithdrawDto withdraw(WithdrawDto withdrawDto) {
        User user = userRepository.findByUsername(getLoggedUser());
        var walletFrom = withdrawDto.getWalletFrom();
        var walletTo= withdrawDto.getWalletTo();
        var amount = withdrawDto.getAmountFrom();
        if((amount+user.getUserWallet(walletFrom.toString())<0.0))
            throw new RuntimeException();
        user.setUserWallet(walletFrom.toString(),amount);
        userRepository.save(user);
        userRepository.flush();
        addTransaction(user,"Withdraw "+ amount.toString()+walletFrom +
                " to " + walletTo);
        withdrawDto.setAmountFrom(user.getUserWallet(walletFrom.toString()));
        return withdrawDto;
    }
    public ConvertDto convertWallets(ConvertDto convertDto) {
        User user=userRepository.findByUsername(getLoggedUser());
        var walletFrom = convertDto.getWalletFrom();
        var walletTo= convertDto.getWalletTo();
        var amountFrom= convertDto.getAmountFrom();
        if((user.getUserWallet(walletFrom.toString())- amountFrom)<0.0) {
            throw new RuntimeException();
        }
        CurrencyRate currencyRate= currencyRateRepository.findByConvertFromAndConvertTo
                (walletFrom,walletTo);
        user.setUserWallet(walletFrom.toString(),(-1)*amountFrom);
        convertDto.setAmountTo(amountFrom*currencyRate.getRatio());
        var amountTo = convertDto.getAmountTo();
        user.setUserWallet(walletTo.toString(),amountTo);
        userRepository.save(user);
        userRepository.flush();
        String trans  = "Convert "+ amountFrom.toString()+walletFrom+
                    " to "+amountTo + walletTo;
        addTransaction(user,trans);
        return convertDto;
    }

    private void addTransaction(User user, String trans) {
        TransactionLog transactionLog= new TransactionLog();
        transactionLog.setUser(user);
        transactionLog.setTrans(trans);
        transactionLogRepository.save(transactionLog);
        transactionLogRepository.flush();
    }
    private String getLoggedUser () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return currentUserName;
    }
}
