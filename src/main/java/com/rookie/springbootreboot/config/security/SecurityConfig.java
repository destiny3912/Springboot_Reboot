package com.rookie.springbootreboot.config.security;

import com.rookie.springbootreboot.config.api.BaseException;
import com.rookie.springbootreboot.config.api.code.BaseErrorCode;
import com.rookie.springbootreboot.config.api.code.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Slf4j
@Configuration
// Production 배포시 debug = true를 지우고 배포
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    /**
     * 내장 비밀번호 인코더 -> 맘에 안들면 암호화 알고리즘 끌어와서 사용할 것
     * */
    @Bean
    public PasswordEncoder encodePassword() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        try {
            http
                    .csrf((csrf) ->
                            csrf.disable())
                    // CORS -> Web과 같이 한다면 끝까지 괴롭힐 요소로 disable하고 사용하는 것을 추천
                    .cors((cors) ->
                            cors.disable())
                    .httpBasic((httpBasic) ->
                            httpBasic.disable())
                    // Jwt 사용한다면 이렇게 STATELESS로 만약 사용한다면 레퍼런스 문서를 보고 할것
                    .sessionManagement((sessionManagement) ->
                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    // 폼 로그인 -> Web 서비스에서는 사용 자유 앱에서는 안하는게 좋음
                    .formLogin((formLogin) ->
                            formLogin.disable())
                    // 리퀘스트 인가 -> 람다안의 request에 메서드 체인으로 설정
                    .authorizeHttpRequests((request) ->
                            request
                                    .requestMatchers("").permitAll()
                                    .anyRequest().authenticated());
                    //.addFilterBefore();

        } catch (Exception exception) {
            log.error("Error in SecurityConfig" + exception.getMessage());
            throw new BaseException(ErrorStatus.INTERNAL_SERVER_ERROR);
        }

        return http.build();
    }
}
