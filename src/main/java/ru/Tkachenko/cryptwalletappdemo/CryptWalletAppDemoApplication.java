package ru.Tkachenko.cryptwalletappdemo;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@SecurityScheme(name="Login",scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class CryptWalletAppDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptWalletAppDemoApplication.class, args);

    }

}
