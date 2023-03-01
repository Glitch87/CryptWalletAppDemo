package ru.Tkachenko.cryptwalletappdemo.repository;

import ru.Tkachenko.cryptwalletappdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    @Query ("select user from User user JOIN user.userWallet wallet where (KEY(wallet))= :walletType")
    List<User> findUserByWalletType(@Param("walletType") String walletType );

}
