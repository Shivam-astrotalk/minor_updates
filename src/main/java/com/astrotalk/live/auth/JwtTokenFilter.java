package com.astrotalk.live.auth;

import org.apache.logging.log4j.LogManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProvider jwtTokenProvider;


	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
			try {
				jwtTokenProvider.validateToken(token, (HttpServletRequest) req);
			} catch (Exception ex) {
				HttpServletResponse response = (HttpServletResponse) res;
				response.sendError(401, ex.getMessage());
				return;
			}

		chain.doFilter(req, res);
	}
}
