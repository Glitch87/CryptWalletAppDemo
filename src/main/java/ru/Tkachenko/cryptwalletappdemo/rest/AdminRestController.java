package ru.Tkachenko.cryptwalletappdemo.rest;

import ru.Tkachenko.cryptwalletappdemo.dto.ExchangeDto;
import ru.Tkachenko.cryptwalletappdemo.service.AdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;


@RestController
@RequestMapping("/admin")
@SecurityRequirement(name="Login")
@Tag(name="Администратор", description="правляет блоками администратора")
public class AdminRestController {
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Operation(summary = "Установка курса валют",
    description = "Позовляет изменить курс валюты относительно других валют криптобиржи")
    @PostMapping(value="/setratio", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ExchangeDto setRatio(@RequestBody ExchangeDto exchangeDto){
        return adminServiceImpl.newExchangeRate(exchangeDto);
    }
    @Operation(summary = "Получить статистику по валюте",
            description = "Позовляет посмотреть общую сумму на всех пользовательских счетах для указанной валюты")
    @GetMapping(value="/getsum", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public Map.Entry<String, String> getSum(@RequestParam (name ="wallet")@RequestBody String walletType) {
        return adminServiceImpl.walletSum(walletType);
    }
    @Operation(summary = "Получить статистику по дате",
            description = "Позовляет посмотреть количество операций, которые были проведены за указанный период")
    @GetMapping(value="/transactioncount", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public Map.Entry<String, String> TransactionCount(@RequestParam (name ="dateFrom") @RequestBody Date dateFrom
            , @RequestParam (name ="dateTo") @RequestBody Date dateTo
                                                      ) {
        return adminServiceImpl.transactionsCount(dateFrom,dateTo);
    }
}
