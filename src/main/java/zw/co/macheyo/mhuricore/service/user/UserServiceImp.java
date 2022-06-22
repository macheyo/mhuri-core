package zw.co.macheyo.mhuricore.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@Service
public class UserServiceImp implements UserService{
    @Autowired
    UserRepository repository;

    @Override
    public Optional<User> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<User>> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<User> create(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) {
        return Optional.empty();
    }

    @Override
    public void delete(User user) {

    }
}
