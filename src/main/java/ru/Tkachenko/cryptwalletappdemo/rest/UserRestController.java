package ru.Tkachenko.cryptwalletappdemo.rest;

import ru.Tkachenko.cryptwalletappdemo.dto.ConvertDto;
import ru.Tkachenko.cryptwalletappdemo.dto.DepositDto;
import ru.Tkachenko.cryptwalletappdemo.dto.WithdrawDto;
import ru.Tkachenko.cryptwalletappdemo.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/user")
@SecurityRequirement(name="Login")
@Tag(name="Пользователь", description="управляет блоками пользователя")
public class UserRestController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Operation(summary = "Просмотр баланса кошелька пользователя",
            description = "Позовляет пользователю посмотреть данные о своем кошельке")
    @GetMapping(value="/getwallets", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public Map<String, String> GetUserWallet(){
        return userServiceImpl.getUserWallets();
    }

    @Operation(summary = "Пополнение кошелька",
            description = "Позовляет пользователю поплнить валюту извне")
    @PostMapping(value="/deposit", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public DepositDto Deposit(@RequestBody DepositDto depositDto){
        return userServiceImpl.deposit(depositDto);
    }
    @Operation(summary = "Вывос с кошелька",
            description = "Позовляет пользователю вывести валюту на внешний кошелек")
    @PostMapping(value="/withdraw", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public WithdrawDto Withdraw(@RequestBody WithdrawDto withdrawDto){
        return userServiceImpl.withdraw(withdrawDto);
    }
    @Operation(summary = "Конвертация валюты",
            description = "Позовляет пользователю обменять валюты по установленному курсу")
    @PostMapping(value="/convert", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ConvertDto Convert(@RequestBody ConvertDto convertDto){
        return userServiceImpl.convertWallets(convertDto);
    }

}
