package com.springboot.app.util;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.app.user.service.CustomeUserDetailsService;

/**
 * JwtRequestFilter extracts JWT from Authorization header value. It further
 * extracts USERNAME from obtained JWT and use that name to load UserDetails
 * Service instance. It then validate JWT with received USerDetails Service
 * instance. On Successful validation it construct
 * UsernamePasswordAuthenticationToken and store it to SecurityContextHolder for
 * future reference.
 * 
 * @author VISHAL
 *
 */

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	CustomeUserDetailsService userdetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		String jwt = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}

		/*
		 * Check validity of JWT with constructed userdetails object from DB data for a
		 * given username. On Valid condition construct
		 * UsernamePasswordAuthenticationToken objectand set that to
		 * SecurityContextHolder.
		 */
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userdetailsService.loadUserByUsername(username);
			if (jwtUtil.validationToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);

			}
		}

		filterChain.doFilter(request, response);

	}

	/**
	 * Get ROLE of current logged-in user.
	 * 
	 * @return
	 */
	public Optional<String> isUser() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(ga -> ga.getAuthority()).filter(ga -> ga.equalsIgnoreCase("ROLE_USER")).findAny();
	}

	/**
	 * Get name of current logged-in user
	 * 
	 * @return
	 */
	public String username() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
