package com.cloud.um.service.impl;

import com.cloud.common.persistence.service.abstracts.AbstractService;
import com.cloud.um.persistence.model.Privilege;
import com.cloud.um.persistence.repository.IPrivilegeRepository;
import com.cloud.um.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService<Privilege> implements IPrivilegeService {

    private IPrivilegeRepository repository;

    @Autowired
    public PrivilegeServiceImpl(IPrivilegeRepository repository) {
        this.repository = repository;

    }

    @Override
    public Privilege findByName(String name) {
        return getRepository().findByName(name);
    }

    @Override
    protected IPrivilegeRepository getRepository() {
        return repository;
    }
}
