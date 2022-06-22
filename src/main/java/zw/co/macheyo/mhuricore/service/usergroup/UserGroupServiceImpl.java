package zw.co.macheyo.mhuricore.service.usergroup;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import zw.co.macheyo.mhuricore.model.UserGroup;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kudzai.Macheyo
 */
@Slf4j
@Service
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    UserGroupRepository repository;
    @Autowired
    UserGroupModelAssembler assembler;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Optional<UserGroup> findById(Object id) {
        return repository.findById((Long) id);
    }

    @Override
    public Optional<UserGroup> create(UserGroup userGroup) {
        return Optional.of(repository.save(modelMapper.map(userGroup, UserGroup.class)));
    }

    @Override
    public Optional<UserGroup> update(UserGroup userGroup) {
        return repository.findById(userGroup.getId()).map(c -> {
                    c.setName(userGroup.getName());
                    c.setDescription(userGroup.getDescription());
                    return repository.save(c);
                });
    }

    @Override
    public void delete(UserGroup userGroup) {
        repository.delete(userGroup);
    }


    @Override
    public List<EntityModel<UserGroup>> findAll() {
        return repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }
}
