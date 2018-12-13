package com.cloud.um.web.controller;

import com.cloud.common.util.QueryConstants;
import com.cloud.common.web.controller.AbstractController;
import com.cloud.common.web.controller.ISortingController;
import com.cloud.um.persistence.model.Privilege;
import com.cloud.um.service.IPrivilegeService;
import com.cloud.um.util.Um;
import com.cloud.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = UmMappings.PRIVILEGES, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PrivilegeController extends AbstractController<Privilege> implements ISortingController<Privilege> {

    @Autowired
    private IPrivilegeService service;

    // find - all/paginated

    @Override
    @GetMapping(params = {QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY})
    public List<Privilege> findAllPaginatedAndSorted(
            @RequestParam(value = QueryConstants.PAGE) int page,
            @RequestParam(value = QueryConstants.SIZE) int size,
            @RequestParam(value = QueryConstants.SORT_BY) String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }

    @Override
    @GetMapping(params = {QueryConstants.PAGE, QueryConstants.SIZE})
    public List<Privilege> findAllPaginated(
            @RequestParam(value = QueryConstants.PAGE) int page,
            @RequestParam(value = QueryConstants.SIZE) int size) {
        return findPaginatedInternal(page, size);
    }

    @Override
    @GetMapping(params = {QueryConstants.SORT_BY})
    public List<Privilege> findAllSorted(
            @RequestParam(value = QueryConstants.SORT_BY) String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @GetMapping
    public List<Privilege> findAll(HttpServletRequest request) {
        return findAllInternal(request);
    }

    // find - one

    @GetMapping(value = "/{id}")
    @CrossOrigin(origins = "*")
    public Privilege findOne(@PathVariable("id") long id) {
        return findOneInternal(id);
    }

    // create

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(value = Um.Privileges.CAN_PRIVILEGE_WRITE)
    public void create(@RequestBody @Valid Privilege resource) {
        createInternal(resource);
    }

    // update

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody @Valid Privilege resource) {
        updateInternal(id, resource);
    }

    // delete

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        deleteByIdInternal(id);
    }

    @Override
    protected IPrivilegeService getService() {
        return service;
    }
}
