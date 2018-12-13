package com.cloud.um.security;

import com.cloud.um.persistence.model.Privilege;
import com.cloud.um.persistence.model.Role;
import com.cloud.um.persistence.model.User;
import com.cloud.um.service.IUserService;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Preconditions.checkNotNull(username);

        final User user = userService.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username was not found: " + username);
        }

        final Set<Role> rolesOfUser = user.getRoles();
        final Set<Privilege> privileges = Sets.newHashSet();
        for (final Role roleOfUser : rolesOfUser) {
            privileges.addAll(roleOfUser.getPrivileges());
        }
        final Function<Object, String> toStringFunction = Functions.toStringFunction();
        final Collection<String> privilegesToString = Collections2.transform(privileges, toStringFunction);
        final String[] privilegeStringsAsArray = privilegesToString.toArray(new String[privilegesToString.size()]);
        final List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(privilegeStringsAsArray);

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), auths);
    }
}
