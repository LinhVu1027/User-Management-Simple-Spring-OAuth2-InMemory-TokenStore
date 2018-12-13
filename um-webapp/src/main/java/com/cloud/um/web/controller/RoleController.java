package com.cloud.um.web.controller;

import com.cloud.common.util.QueryConstants;
import com.cloud.common.web.controller.AbstractController;
import com.cloud.common.web.controller.ISortingController;
import com.cloud.um.persistence.model.Role;
import com.cloud.um.service.IRoleService;
import com.cloud.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = UmMappings.ROLES, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController extends AbstractController<Role> implements ISortingController<Role> {

    @Autowired
    private IRoleService service;

    // find - all/paginated

    @Override
    @GetMapping(params = {QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY})
    public List<Role> findAllPaginatedAndSorted(
            @RequestParam(value = QueryConstants.PAGE) int page,
            @RequestParam(value = QueryConstants.SIZE) int size,
            @RequestParam(value = QueryConstants.SORT_BY) String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }

    @Override
    @GetMapping(params = {QueryConstants.PAGE, QueryConstants.SIZE})
    public List<Role> findAllPaginated(
            @RequestParam(value = QueryConstants.PAGE) int page,
            @RequestParam(value = QueryConstants.SIZE) int size) {
        return findPaginatedInternal(page, size);
    }

    @Override
    @GetMapping(params = {QueryConstants.SORT_BY})
    public List<Role> findAllSorted(
            @RequestParam(value = QueryConstants.SORT_BY) String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @GetMapping
    public List<Role> findAll(HttpServletRequest request) {
        return findAllInternal(request);
    }

    // find - one

    @GetMapping(value = "/{id}")
    public Role findOne(@PathVariable("id") long id) {
        return findOneInternal(id);
    }

    // create

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Role resource) {
        createInternal(resource);
    }

    // update

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Role resource) {
        updateInternal(id, resource);
    }

    // delete

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        deleteByIdInternal(id);
    }

    @Override
    protected IRoleService getService() {
        return service;
    }
}
