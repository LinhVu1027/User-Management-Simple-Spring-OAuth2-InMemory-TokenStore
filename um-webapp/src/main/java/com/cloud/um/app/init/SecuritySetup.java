package com.cloud.um.app.init;

import com.cloud.um.persistence.model.Privilege;
import com.cloud.um.persistence.model.Role;
import com.cloud.um.persistence.model.User;
import com.cloud.um.service.IPrivilegeService;
import com.cloud.um.service.IRoleService;
import com.cloud.um.service.IUserService;
import com.cloud.um.util.Um;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class SecuritySetup implements ApplicationListener<ContextRefreshedEvent> {

    private boolean setupDone;

    private IUserService userService;
    private IRoleService roleService;
    private IPrivilegeService privilegeService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecuritySetup(IUserService userService,  IRoleService roleService, IPrivilegeService privilegeService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!setupDone) {
            log.info("Executing Setup");

            createPrivileges();
            createRoles();
            createUsers();

            setupDone = true;
            log.info("Setup Done");
        }
    }

    // Privilege

    private void createPrivileges() {
        createPrivilegeIfNotExisting(Um.Privileges.CAN_PRIVILEGE_READ);
        createPrivilegeIfNotExisting(Um.Privileges.CAN_PRIVILEGE_WRITE);

        createPrivilegeIfNotExisting(Um.Privileges.CAN_ROLE_READ);
        createPrivilegeIfNotExisting(Um.Privileges.CAN_ROLE_WRITE);

        createPrivilegeIfNotExisting(Um.Privileges.CAN_USER_READ);
        createPrivilegeIfNotExisting(Um.Privileges.CAN_USER_WRITE);
    }

    final void createPrivilegeIfNotExisting(final String name) {
        final Privilege entityByName = privilegeService.findByName(name);
        if (entityByName == null) {
            final Privilege entity = new Privilege(name);
            privilegeService.create(entity);
        }
    }

    // Role

    private void createRoles() {
        final Privilege canPrivilegeRead = privilegeService.findByName(Um.Privileges.CAN_PRIVILEGE_READ);
        final Privilege canPrivilegeWrite = privilegeService.findByName(Um.Privileges.CAN_PRIVILEGE_WRITE);
        final Privilege canRoleRead = privilegeService.findByName(Um.Privileges.CAN_ROLE_READ);
        final Privilege canRoleWrite = privilegeService.findByName(Um.Privileges.CAN_ROLE_WRITE);
        final Privilege canUserRead = privilegeService.findByName(Um.Privileges.CAN_USER_READ);
        final Privilege canUserWrite = privilegeService.findByName(Um.Privileges.CAN_USER_WRITE);

        Preconditions.checkNotNull(canPrivilegeRead);
        Preconditions.checkNotNull(canPrivilegeWrite);
        Preconditions.checkNotNull(canRoleRead);
        Preconditions.checkNotNull(canRoleWrite);
        Preconditions.checkNotNull(canUserRead);
        Preconditions.checkNotNull(canUserWrite);

        createRoleIfNotExisting(Um.Roles.ROLE_ENDUSER, Sets.<Privilege> newHashSet(canUserRead, canRoleRead, canPrivilegeRead));
        createRoleIfNotExisting(Um.Roles.ROLE_ADMIN, Sets.<Privilege> newHashSet(canUserRead, canUserWrite, canRoleRead, canRoleWrite, canPrivilegeRead, canPrivilegeWrite));
    }

    final void createRoleIfNotExisting(final String name, final Set<Privilege> privileges) {
        final Role entityByName = roleService.findByName(name);
        if (entityByName == null) {
            final Role entity = new Role(name);
            entity.setPrivileges(privileges);
            roleService.create(entity);
        }
    }

    // User/User

    final void createUsers() {
        final Role roleAdmin = roleService.findByName(Um.Roles.ROLE_ADMIN);
        final Role roleUser = roleService.findByName(Um.Roles.ROLE_ENDUSER);

        // createUserIfNotExisting(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASS, Sets.<Role> newHashSet(roleAdmin));
        createUserIfNotExisting(Um.ADMIN_USERNAME, Um.ADMIN_PASS, Sets.<Role> newHashSet(roleAdmin));
        createUserIfNotExisting(Um.USER_USERNAME, Um.USER_PASS, Sets.<Role> newHashSet(roleUser));
    }

    final void createUserIfNotExisting(final String loginName, final String pass, final Set<Role> roles) {
        final User entityByName = userService.findByName(loginName);
        if (entityByName == null) {
            String encode = passwordEncoder.encode(pass);
            final User entity = new User(loginName, encode, roles);
            userService.create(entity);
        }
    }
}
