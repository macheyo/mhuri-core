package zw.co.macheyo.mhuricore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.model.Account;
import zw.co.macheyo.mhuricore.model.Transaction;
import zw.co.macheyo.mhuricore.modelAssembler.TransactionModelAssembler;
import zw.co.macheyo.mhuricore.repository.TransactionRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Transaction save(Transaction transaction, HttpServletRequest httpServletRequest) {
        transaction.setCreatedBy(httpServletRequest.getUserPrincipal().getName());
        transaction.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
        return transactionRepository.save(modelMapper.map(transaction, Transaction.class));
    }

    @Override
    public Transaction update(Long id, Transaction transaction, HttpServletRequest httpServletRequest) {
        return transactionRepository.findById(id).map(t->{
                    t.setLastModifiedBy(httpServletRequest.getUserPrincipal().getName());
                    t.setLastModifiedDate(LocalDateTime.now());
                    return transactionRepository.save(t);})
                .orElseThrow(()->new ResourceNotFoundException("transaction","id",id));
    }

    @Override
    public List<EntityModel<Transaction>> findAll() {
        return transactionRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("transaction","id",id));
    }
}
