package ru.Tkachenko.cryptwalletappdemo.rest;

import ru.Tkachenko.cryptwalletappdemo.dto.RegistrationDto;
import ru.Tkachenko.cryptwalletappdemo.service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
@Tag(name="Регистрация", description="регистрация пользователя")
public class AuthController {
    @Autowired
    private AuthServiceImpl autService;
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя")
    @PostMapping(value="/registration", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public RegistrationDto RegistrationUser(@Validated @RequestBody RegistrationDto registrationDto){
        return autService.addUser(registrationDto);
    }
    @Operation(summary = "Просмотр актуальных курсов валют ",
            description = "Позовляет пользователю или администратору посмотреть данные о текущих курсах валют")
    @GetMapping(value="/getratios", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public Map<String,String> GetRatios(
            @RequestParam (name ="wallet")@RequestBody String walletType){
        return autService.showRatios(walletType);
    }
}
