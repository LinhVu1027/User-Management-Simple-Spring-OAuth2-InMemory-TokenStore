package com.cloud.common.persistence.service.abstracts;

import com.cloud.common.persistence.ServicePreconditions;
import com.cloud.common.persistence.service.IRawService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
public abstract class AbstractRawService<T extends Serializable> implements IRawService<T> {

    public AbstractRawService() {
        super();
    }

    // find - one

    @Override
    @Transactional(readOnly = true)
    public T findOne(final long id) {
        Optional<T> entity = getRepository().findById(id);
        return entity.orElse(null);
    }

    // find - all

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getRepository().findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Sort sort = constructSort(sortBy, sortOrder);
        return Lists.newArrayList(getRepository().findAll(sort));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllPaginated(final int page, final int size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return getRepository().findAll(pageRequest).getContent();
    }

    @Override
    public List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final Sort sort = constructSort(sortBy, sortOrder);
        final PageRequest pageRequest = PageRequest.of(page, size, sort);
        return getRepository().findAll(pageRequest).getContent();
    }

    @Override
    public Page<T> findAllPaginatedRaw(final int page, final int size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return getRepository().findAll(pageRequest);
    }

    @Override
    public Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder) {
        final Sort sort = constructSort(sortBy, sortOrder);
        final PageRequest pageRequest = PageRequest.of(page, size, sort);
        return getRepository().findAll(pageRequest);
    }

    // save/create/persist

    @Override
    public T create(final T entity) {
        ServicePreconditions.checkEntityExists(entity);
        final T persistedEntity = getRepository().save(entity);
        return persistedEntity;
    }

    // update/merge

    @Override
    public void update(final T entity) {
        ServicePreconditions.checkEntityExists(entity);
        getRepository().save(entity);
    }

    // delete

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }

    @Override
    public void delete(final long id) {
        final Optional<T> entity = getRepository().findById(id);
        if (entity.isPresent()) {
            ServicePreconditions.checkEntityExists(entity);
            getRepository().delete(entity.get());
        }
    }

    // count

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return getRepository().count();
    }

    // template method

    protected abstract PagingAndSortingRepository<T, Long> getRepository();

    // template

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = new Sort(Sort.Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }
}
