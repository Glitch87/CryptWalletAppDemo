package ru.Tkachenko.cryptwalletappdemo.service;

import ru.Tkachenko.cryptwalletappdemo.dto.RegistrationDto;
import ru.Tkachenko.cryptwalletappdemo.entity.CurrencyRate;
import ru.Tkachenko.cryptwalletappdemo.entity.Role;
import ru.Tkachenko.cryptwalletappdemo.entity.User;
import ru.Tkachenko.cryptwalletappdemo.entity.Wallet;
import ru.Tkachenko.cryptwalletappdemo.repository.CurrencyRateRepository;
import ru.Tkachenko.cryptwalletappdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("authService")
public class AuthServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        User userName = userRepository.findByUsername(string);
        User userEmail = userRepository.findByEmail(string);
        if ((userName == null) && (userEmail== null)) {
            throw new UsernameNotFoundException ("User not found");
        }
        if (userName == null)
            return userEmail;
        else return userName;
    }
    public RegistrationDto addUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setUsername(registrationDto.getUsername());
        if ((userRepository.findByUsername(user.getUsername()) == null) &&
                (userRepository.findByEmail(user.getEmail()) == null)) {
            user.setRoles(Role.USER);
            user.setSecretKey();
            user.setPassword();
            for (Wallet wallet : Wallet.values()) {
                user.setUserWallet(wallet.name(), 0.0);
            }
            userRepository.save(user);
            userRepository.flush();
            return new RegistrationDto(user.getSecretKey());
        } else throw new RuntimeException("User already exists");
    }
    public Map<String,String> showRatios(String walletType) {
        List<CurrencyRate> currencyRates = currencyRateRepository.
                findAllByConvertFrom(Wallet.valueOf(walletType));
        Map<String,String> map = new HashMap<>();
        for(CurrencyRate currencyRate:currencyRates) {
            map.put(currencyRate.getConvertTo().toString(), currencyRate.getRatio().toString());
        }
        return map;
    }
}
