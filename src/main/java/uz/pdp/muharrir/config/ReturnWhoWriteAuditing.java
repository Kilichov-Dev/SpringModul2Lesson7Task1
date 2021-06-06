package uz.pdp.muharrir.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.pdp.muharrir.entity.User;

@Configuration
@EnableJpaAuditing
public class ReturnWhoWriteAuditing {
    AuditorAware<User> auditorAware() {
        return new KnowWhoAuditing();
    }

}
