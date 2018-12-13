package com.cloud.um.service.impl;

import com.cloud.common.persistence.service.abstracts.AbstractService;
import com.cloud.um.persistence.model.User;
import com.cloud.um.persistence.repository.IUserRepository;
import com.cloud.um.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    private IUserRepository repository;

    @Autowired
    public UserServiceImpl(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByName(String name) {
        return getRepository().findByName(name);
    }

    @Override
    protected IUserRepository getRepository() {
        return repository;
    }
}
