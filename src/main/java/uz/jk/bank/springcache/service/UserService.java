package uz.jk.bank.springcache.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.jk.bank.springcache.entity.UserEntity;
import uz.jk.bank.springcache.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private int count = 0;
//    private final Cache cache;
//    public UserService(UserRepository userRepository, CacheManager cacheManager) {
//        this.userRepository = userRepository;
//        this.cache = cacheManager.getCache("users");
//    }


    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

//    public UserEntity findById(Long id) {
//        if (cache.get(id, UserEntity.class) != null) {
//            return cache.get(id, UserEntity.class);
//        }
//
//        UserEntity userEntity = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        cache.put(id, userEntity);
//        return userEntity;
//    }

    @SneakyThrows
    @Cacheable(value = "users", key = "#id")
    public UserEntity findById(Long id) {
        Thread.sleep(1000);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @SneakyThrows
    @Cacheable(value = "users", key="#root.methodName")
    public List<UserEntity> findAll() {
        Thread.sleep(3000);
        return userRepository.findAll();
    }


    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
//        cache.evict(id);
        userRepository.deleteById(id);
    }

    @CachePut(value = "users", key = "#user.id")
    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

}
