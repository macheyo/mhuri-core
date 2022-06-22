package zw.co.macheyo.mhuricore.common;

import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;

/**
 * @author Kudzai.Macheyo
 */
public interface AppService<T>{
    Optional<T> findById(Object id);

    List<EntityModel<T>> findAll();

    Optional<T> create(T t);

    Optional<T> update(T t);

    void delete(T t);
}
