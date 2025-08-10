package org.danji.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.security.filter.AuthenticationErrorFilter;
import org.danji.security.filter.JwtAuthenticationFilter;
import org.danji.security.filter.JwtUsernamePasswordAuthenticationFilter;
import org.danji.security.handler.CustomAccessDeniedHandler;
import org.danji.security.handler.CustomAuthenticationEntryPoint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Log4j2
@MapperScan(basePackages = {"org.danji.member.mapper"})
@ComponentScan(basePackages = {"org.danji.security"})
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationErrorFilter authenticationErrorFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    // 커스텀 인증 필터 추가
    @Autowired
    private JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 인증 에러 필터 - 가장 먼저 실행
                .addFilterBefore(
                        authenticationErrorFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                // JWT 인증 필터
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                // 로그인 인증 필터
                .addFilterBefore(
                        jwtUsernamePasswordAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                // 예외 처리 설정
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)  // 401 에러 처리
                .accessDeniedHandler(accessDeniedHandler);           // 403 에러 처리

        //  HTTP 보안 설정
        http.cors().and()
                .httpBasic().disable()      // 기본 HTTP 인증 비활성화
                .csrf().disable()           // CSRF 보호 비활성화 (REST API에서는 불필요)
                .formLogin().disable()      // 폼 로그인 비활성화 (JSON 기반 API 사용)
                .sessionManagement()        // 세션 관리 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // 무상태 모드

        // 경로별, 접근 권한 설정
        http
                .authorizeRequests() // 경로별 접근 권한 설정
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/",   // 실행 시 swagger로 바로 redirect
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .antMatchers("/api/members/login", "/api/members", "/api/health").permitAll()
                .antMatchers("/static/images/**").permitAll()
                .anyRequest().authenticated(); // 나머지는 로그인 필요
    }


    // 직접 만든 userDetailsService를 이용해 인증을 진행하도록 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("configure .........................................");
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // CORS 설정 객체 생성
        CorsConfiguration cfg = new CorsConfiguration();

        // 권장: 정확한 오리진만 허용 (credentials=true일 때 와일드카드 지양)
        cfg.setAllowedOrigins(List.of("https://danji.vercel.app", "http://localhost:5173/", "https://danji.cloud/"));
        // 또는 패턴을 꼭 써야 하면 아래를 쓰되 보안상 비추천
        // cfg.setAllowedOriginPatterns(List.of("https://*.vercel.app"));

        // 프리플라이트 포함
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));

        // 필요한 헤더만 (Authorization, Content-Type 정도) — 불필요하면 CORS 자체가 덜 일어남
        cfg.setAllowedHeaders(List.of("*"));

        // 응답에서 노출할 헤더가 필요하면
        // cfg.setExposedHeaders(List.of("Location"));

        // 쿠키/Authorization 사용 시
        cfg.setAllowCredentials(true);

        // ★ 프리플라이트 캐시 24시간ㅎ
        cfg.setMaxAge(86_400L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy(" ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

}
