package com.fmss.hr.configuration.security;

import com.fmss.hr.common.constant.ExceptionMessages;
import com.fmss.hr.exceptions.CustomException;
import com.fmss.hr.repos.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return (UserDetails) userRepository.findByEmail(username)
                .orElseThrow(() -> new CustomException(ExceptionMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
