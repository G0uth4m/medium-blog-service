package com.goutham.mediumblogservice.security.util;

import com.goutham.mediumblogservice.enums.UserRole;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

  public static Set<UserRole> getRoles() {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .map(grantedAuthority -> UserRole.valueOf(grantedAuthority.getAuthority()))
        .collect(Collectors.toSet());
  }

  public static String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

}
