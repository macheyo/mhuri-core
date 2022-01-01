package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Customer;
import zw.co.macheyo.mhuricore.modelAssembler.CustomerModelAssembler;
import zw.co.macheyo.mhuricore.repository.CustomerRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Customer save(Customer customer, HttpServletRequest httpServletRequest) {
        customer.setCreatedBy(httpServletRequest.getUserPrincipal().getName());
        customer.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
        return customerRepository.save(modelMapper.map(customer, Customer.class));
    }

    @Override
    public Customer update(Long id, Customer customer, HttpServletRequest httpServletRequest) {
        return customerRepository.findById(id).map(c->{
                    c.setName(customer.getName());
                    c.setMsisdn(customer.getMsisdn());
                    c.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
                    c.setLastModifiedDate(LocalDateTime.now());
                    return customerRepository.save(c);})
                .orElseThrow(()->new ResourceNotFoundException("customer","id",id));
    }

    @Override
    public List<EntityModel<Customer>> findAll() {
        return customerRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("customer","id",id));
    }
}
