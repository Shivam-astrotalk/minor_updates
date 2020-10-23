package com.astrotalk.live.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	  ROLE_ADMIN, ROLE_USER, ROLE_CONSULTANT, ROLE_MERCHANT, ROLE_AFFILIATE;

	  public String getAuthority() {
	    return name();
	  }

	}
