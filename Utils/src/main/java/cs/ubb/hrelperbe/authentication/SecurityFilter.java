package cs.ubb.hrelperbe.authentication;

import cs.ubb.hrelperbe.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Security filter for processing incoming HTTP requests and extracting JWT tokens.
 * This filter is executed once per request and is responsible for validating tokens
 * and setting up the security context for authenticated users.
 */
@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);
        if (token != null) {
            var userId = tokenProvider.validateToken(token);
            var role = tokenProvider.extractRole(token); // EXTRACT ROLE

            if (userId != null && role != null) {
                // Create a GrantedAuthority from the role
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                // Set authentication with the role as authority
                var authentication = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        Collections.singletonList(authority) // ADD AUTHORITIES HERE
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new CustomException("Invalid JWT", HttpStatus.UNAUTHORIZED);
            }
        }

        filterChain.doFilter(request, response);
    }

    public String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}