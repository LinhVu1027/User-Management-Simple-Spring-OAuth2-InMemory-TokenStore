package com.cloud.um.persistence.repository;

import com.cloud.common.interfaces.api.IByNameApi;
import com.cloud.um.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User>,
        IByNameApi<User> {
}
