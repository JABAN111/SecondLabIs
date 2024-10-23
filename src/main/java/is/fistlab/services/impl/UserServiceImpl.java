package is.fistlab.services.impl;

import is.fistlab.database.entities.User;
import is.fistlab.database.repositories.UserRepository;
import is.fistlab.services.UserService;
import is.fistlab.utils.PasswordProcessing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final static String SALT = "D&D";
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User createNewUser(User user) {
        user.setPassword(PasswordProcessing.encryptPassword(user.getPassword(),SALT.getBytes()));


        User newUser = userRepository.save(user);
        log.info("{} registered successfully",user.getUsername());

        return newUser;
    }
    @Transactional
    @Override
    public User updateUser(User user) {
        userRepository.getReferenceById(user.getId());
        user.setPassword(PasswordProcessing.encryptPassword(user.getPassword(),SALT.getBytes()));
        User newUser = userRepository.save(user);
        log.info("{} updated successfully",user.getUsername());
        return newUser;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("User with id: {} deleted successfully",id);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            log.info("User with id: {} found", id);
            return user.get();
        }
        log.error("User with id: {} not found", id);
        return null;
    }

    @Override
    public boolean isUserExists(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            log.info("User with username: {} found", username);
            return true;
        }
        log.info("User with username: {} not found", username);
        return false;
    }

    @Override
    public User getUser(User user) {
        if (!isUserExists(user.getUsername())){
            return null;//todo добавить кастомные ошибки
//            throw new ChangeSetPersister.NotFoundException("User with username: " + user.getUsername() + " not found");
        }
        return userRepository.findByUsername(user.getUsername());
    }

    public static String passwordToHash(String password) {
        return PasswordProcessing.encryptPassword(password,SALT.getBytes());
    }
}
