package zw.co.macheyo.mhuricore.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.model.Contact;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepository repository;
    @Autowired
    ContactModelAssembler assembler;

    @Override
    public Optional<Contact> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public Optional<Contact> create(Contact contact) {
        return Optional.of(repository.save(contact));
    }

    @Override
    public Optional<Contact> update(Contact contact) {
        return repository.findById(contact.getId()).map(c -> {
                    c.setName(contact.getName());
                    c.setMsisdn(contact.getMsisdn());
                    c.setContactType(contact.getContactType());
                    return repository.save(c);
                });
    }

    @Override
    public void delete(Contact contact) {
        repository.delete(contact);
    }


    @Override
    public List<EntityModel<Contact>> findAll() {
        return repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }
}
