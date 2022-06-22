package zw.co.macheyo.mhuricore.service.role;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository repository;
    @Autowired
    RoleModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Optional<Role> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public List<EntityModel<Role>> findAll() {
        return repository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Role> create(Role role) {
        return Optional.of(repository.save(modelMapper.map(role, Role.class)));
    }

    @Override
    public Optional<Role> update(Role role) {
        return repository.findById(role.getId()).map(r->{
            r.setName(role.getName());
            r.setDescription(role.getDescription());
            return repository.save(r);
        });
    }

    @Override
    public void delete(Role role) {
        repository.delete(role);
    }
}
