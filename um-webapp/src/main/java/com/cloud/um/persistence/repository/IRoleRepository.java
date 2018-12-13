package com.cloud.um.persistence.repository;

import com.cloud.common.interfaces.api.IByNameApi;
import com.cloud.um.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleRepository extends JpaRepository<Role, Long>,
        JpaSpecificationExecutor<Role>,
        IByNameApi<Role> {
}
