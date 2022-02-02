package zw.co.macheyo.mhuricore.service;

import org.springframework.hateoas.EntityModel;
import zw.co.macheyo.mhuricore.model.Contact;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ContactService {
    Contact save(Contact contact);

    Contact update(Long id, Contact contact);

    List<EntityModel<Contact>> findAll();

    Contact findById(Long id);

    void deleteById(Long id);
}
