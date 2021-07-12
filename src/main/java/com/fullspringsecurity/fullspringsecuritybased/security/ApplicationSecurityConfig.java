package com.fullspringsecurity.fullspringsecuritybased.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private PasswordConfig passwordConfig;
//
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http
                .csrf().disable()// TODO,, WILL BE EXPLAINED LATER
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                /* role based*/ .antMatchers("/api/v1/adm").hasRole(ApplicationsUserRole.ADMIN.name())
                .antMatchers("/api/v1/student").hasRole(ApplicationsUserRole.STUDENT.name())
              //  .antMatchers(HttpMethod.POST,"/api/vi/createStudent").hasAuthority(ApplicationUserPermissions.STUDENT_WRITE.name())
                .antMatchers(HttpMethod.POST,"/api/vi/createStudent").hasAuthority(ApplicationUserPermissions.STUDENT_WRITE.getPermission())
              //  .antMatchers(HttpMethod.DELETE,"/api/v1/getAllstudent").hasAnyRole(ApplicationsUserRole.ADMIN.name(),ApplicationsUserRole.ADMIN2.name())
                .antMatchers(HttpMethod.DELETE,"/api/v1/deleteStudent/**").hasAuthority(ApplicationUserPermissions.STUDENT_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();// we are using basic authentication  // drawback==> you cannot log out, username and password
                             //sent on every request and the server must validate the request every now and then
    }

    //inMEMEORYUserDetailManager
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user=User.builder()
                .username("Nelson")
               // .password("Nelson")//noPasswordEncrption
                .password(passwordEncoder.encode("Nelson"))
                .authorities(ApplicationsUserRole.ADMIN.grantedAuthorities())
             //   .roles(ApplicationsUserRole.ADMIN.name())//ROLE_ADMIN
                .build();

        UserDetails user1=User.builder()
                .username("Moses")
                .password(passwordEncoder.encode("Moses"))
                .authorities(ApplicationsUserRole.STUDENT.grantedAuthorities())
             //   .roles(ApplicationsUserRole.STUDENT.name())// ROLE_STUDENT
                .build();

        UserDetails user2=User.builder()
                .username("Otieno")
                .password(passwordEncoder.encode("Otieno"))
                .authorities(ApplicationsUserRole.ADMIN2.grantedAuthorities())
            //    .roles(ApplicationsUserRole.ADMIN2.name()) // ROLE_ADMIN2  // has Read permissions only, the other admin has read and write permissions
                .build();

        return  new InMemoryUserDetailsManager(user,user1,user2);
    }
}
