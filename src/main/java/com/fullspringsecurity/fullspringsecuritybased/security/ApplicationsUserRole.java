package com.fullspringsecurity.fullspringsecuritybased.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationsUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            ApplicationUserPermissions.COURSE_READ,
            ApplicationUserPermissions.COURSE_WRITE,
            ApplicationUserPermissions.STUDENT_READ,
            ApplicationUserPermissions.STUDENT_WRITE)),

    ADMIN2(Sets.newHashSet(
            ApplicationUserPermissions.COURSE_READ,
          ApplicationUserPermissions.STUDENT_READ));

    private final Set<ApplicationUserPermissions> permissions;

    ApplicationsUserRole(Set<ApplicationUserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermissions> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> grantedAuthorities(){
        Set<GrantedAuthority> p= getPermissions().stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toSet());
        p.add(new SimpleGrantedAuthority("ROLE_" +this.name()));
        return  p;
    }
}
