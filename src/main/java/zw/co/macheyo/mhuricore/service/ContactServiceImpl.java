package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Contact;
import zw.co.macheyo.mhuricore.modelAssembler.ContactModelAssembler;
import zw.co.macheyo.mhuricore.repository.ContactRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ContactModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Contact save(Contact contact, HttpServletRequest httpServletRequest) {
        contact.setCreatedBy(httpServletRequest.getUserPrincipal().getName());
        contact.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
        return contactRepository.save(modelMapper.map(contact, Contact.class));
    }

    @Override
    public Contact update(Long id, Contact contact, HttpServletRequest httpServletRequest) {
        return contactRepository.findById(id).map(c->{
                    c.setName(contact.getName());
                    c.setMsisdn(contact.getMsisdn());
                    c.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
                    c.setLastModifiedDate(LocalDateTime.now());
                    return contactRepository.save(c);})
                .orElseThrow(()->new ResourceNotFoundException("contact","id",id));
    }

    @Override
    public List<EntityModel<Contact>> findAll() {
        return contactRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("contact","id",id));
    }

    @Override
    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }
}
