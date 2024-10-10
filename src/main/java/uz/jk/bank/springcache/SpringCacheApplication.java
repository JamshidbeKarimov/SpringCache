package uz.jk.bank.springcache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import uz.jk.bank.springcache.entity.UserEntity;
import uz.jk.bank.springcache.repository.UserRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class SpringCacheApplication {

    private static ObjectMapper objectMapper;
    private static UserRepository userRepository;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        SpringCacheApplication.objectMapper = objectMapper;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        SpringCacheApplication.userRepository = userRepository;
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringCacheApplication.class, args);
        try {
            addValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addValue() throws IOException {
        List<UserEntity> userEntities = objectMapper.readValue(
                new URL("https://jsonplaceholder.typicode.com/users"),
                new TypeReference<List<UserEntity>>() {
                });

        userRepository.saveAll(userEntities);
    }
}
