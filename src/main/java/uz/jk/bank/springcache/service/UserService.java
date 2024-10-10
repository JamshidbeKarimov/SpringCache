package uz.jk.bank.springcache.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import uz.jk.bank.springcache.entity.UserEntity;
import uz.jk.bank.springcache.repository.UserRepository;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConcurrentHashMap<Long, UserEntity> cacheMap = new ConcurrentHashMap<>();


    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    @SneakyThrows
    public UserEntity findById(Long id) {
        return cacheMap.computeIfAbsent(id, (key) -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return userRepository.findById(key)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        });

//        if (cacheMap.get(id) != null) {
//            return cacheMap.get(id);
//        }
//        Thread.sleep(2000);
//        UserEntity userEntity = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        cacheMap.put(userEntity.getId(), userEntity);
//        return userEntity;
    }

    public void deleteUser(Long id) {
        cacheMap.remove(id);
        userRepository.deleteById(id);

    }

    public UserEntity updateUser(UserEntity user) {
        cacheMap.put(user.getId(), user);
        return userRepository.save(user);
    }
}
