package com.cloud.um.service.impl;

import com.cloud.common.persistence.service.abstracts.AbstractService;
import com.cloud.um.persistence.model.Role;
import com.cloud.um.persistence.repository.IRoleRepository;
import com.cloud.um.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements IRoleService {

    private IRoleRepository repository;

    @Autowired
    public RoleServiceImpl(IRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role findByName(String name) {
        return getRepository().findByName(name);
    }

    @Override
    protected IRoleRepository getRepository() {
        return repository;
    }
}
