package br.com.pedro.usermicroservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
//utilizamos esta classe para apenas deixar os endpoints serem acessados quando o usuário estiver logado.

@Configuration
//Este prePostEnabled=true libera para nós o @PreAuthorize e @PostAuthorize.
//Adicionando eles em métodos podemos fazer a verificação de login.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalSecurityConfig extends GlobalMethodSecurityConfiguration {
}
