package com.cloud.um.persistence.repository;

import com.cloud.common.interfaces.api.IByNameApi;
import com.cloud.um.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPrivilegeRepository extends JpaRepository<Privilege, Long>,
        JpaSpecificationExecutor<Privilege>,
        IByNameApi<Privilege> {
}
