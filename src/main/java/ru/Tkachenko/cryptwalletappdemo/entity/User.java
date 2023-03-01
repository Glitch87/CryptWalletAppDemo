package ru.Tkachenko.cryptwalletappdemo.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="USERNAME", nullable = false, unique = true)
    private String username;
    @Column(name="EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name="SECRET_KEY", nullable = false, unique = true)
    private String secretKey;
    @Column(name="password", nullable = false, unique = true)
    private String password;
    @Column(name="ROLE", nullable = false)
    @Enumerated(value=EnumType.STRING)
    private Role role;

    @ElementCollection
    @CollectionTable(name = "wallets", joinColumns = @JoinColumn(name = "user_id"))
    @PrimaryKeyJoinColumn(name = "wallet_type")
    @Column(name = "ammout")
    private Map<String, Double> userWallet=new HashMap<>();
    public User() {}
    public User(String email, String username) {
        this.email = email;
        this.username=username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword() {
        password = new BCryptPasswordEncoder().encode(secretKey);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public UUID setSecretKey() {
        byte[] byteArr=(username+email).getBytes(StandardCharsets.UTF_8);
        UUID uuid = UUID.nameUUIDFromBytes(byteArr);
        secretKey=uuid.toString();
        return uuid;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getUsername() {
       return username;
    }
    public void setEmail(String email) {
        this.email=email;
    }
    public String getEmail() {
        return email;
    }
    public void setRoles(Role role) {
        this.role = role;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Role getRole() {
        return role;
    }

    public void setUserWallet(String walletType, Double value) {
        Double base=userWallet.get(walletType);
        if (base==null)
            base=0.0;
        userWallet.put(walletType,value+base);
    }
    public Double getUserWallet(String walletType) {
        return userWallet.get(walletType);
    }
    public Map<String,String> getMap() {
        Map<String,String> map=new HashMap<>();
        for(Map.Entry<String,Double> entry : userWallet.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }
    public Set<SimpleGrantedAuthority> getRoles() {
        SimpleGrantedAuthority auth= new SimpleGrantedAuthority(role.toString());
        return Set.of(auth);
    }

}
