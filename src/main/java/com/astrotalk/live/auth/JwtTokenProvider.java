package com.astrotalk.live.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
public class JwtTokenProvider {


	@Value("${JWT_SECRET}")
	private String jwtSecret;

	@Value("${LIVE_SERVER}")
	String liveServer;

	private long oneDay = 24 * 60 * 60 * 1000;
	int defaultExpiryTime = 10 * 60 * 1000;
	int defaultExpiryTimeNotification = 5 * 60 * 1000;



	@PostConstruct
	protected void init() {
		jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
	}

	public Boolean getIsLive(String token) {
		if(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().containsKey("isLive"))
			return Boolean.parseBoolean(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("isLive")+"");
		else
			return null;
	}

	public Long getId(String token) {
		return Long.parseLong(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("id") + "");
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	public String getUserEmail(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public Authentication getAuthentication(String token) {
		Role role_type = Role.valueOf(getRoleType(token));
		String email = getUserEmail(token);
		UserDetails userDetails = new User(email,email,true,true,true,true, new ArrayList<Role>(Arrays.asList(role_type)));
		return new UsernamePasswordAuthenticationToken(userDetails, getId(token), userDetails.getAuthorities());
	}

	public boolean validateToken(String token, HttpServletRequest req) throws Exception {
		try {
			if (!resolveId(token, req))
				throw new Exception("Id Mismatch");
//			String role_type = getRoleType(token);
//			if (role_type.equals(Role.ROLE_ADMIN.toString())) {
//				if (!resolveParamId(token, req))
//					throw new CustomException("Id Param Mismatch", HttpStatus.BAD_REQUEST);
//			}

			Boolean isLive = getIsLive(token);

			if(isLive!=null && isLive && liveServer.equalsIgnoreCase("live"))
				throw new Exception("Token Server Mismatch - " + isLive);

			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			req.setAttribute("X_ROLE_",getRoleType(token));return true;
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			throw new Exception("Expired or invalid JWT token");
		}
	}

	public String getRoleType(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("role_type").toString();
	}
	public boolean resolveId(String token, HttpServletRequest req) {
		String id = req.getHeader("id");
		String tokenId = getId(token) + "";
		if (id != null && !id.isEmpty() && id.equals(tokenId)) {
			return true;
		}
		return false;
	}

}
