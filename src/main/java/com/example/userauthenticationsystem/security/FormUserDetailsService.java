package com.example.userauthenticationsystem.security;

import com.example.userauthenticationsystem.domain.entity.Account;
import com.example.userauthenticationsystem.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")//클래스 명은 이게 아니지만 빈 이름은 이렇게 한다.
@RequiredArgsConstructor
public class FormUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find by Username : " + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.getRoles()));
        return new CustomUserDetails(account, authorities);
    }
}
