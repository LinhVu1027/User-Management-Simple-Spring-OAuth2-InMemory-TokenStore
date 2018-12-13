package com.cloud.common.persistence.service;

import com.cloud.common.interfaces.repository.IOperation;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface IRawService<T extends Serializable> extends IOperation<T> {

    Page<T> findAllPaginatedAndSortedRaw(int page, int size, String sortBy, String sortOrder);
    Page<T> findAllPaginatedRaw(int page, int size);

}
