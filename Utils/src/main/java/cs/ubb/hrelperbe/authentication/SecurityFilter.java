package cs.ubb.hrelperbe.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Security filter for processing incoming HTTP requests and extracting JWT tokens.
 * This filter is executed once per request and is responsible for validating tokens
 * and setting up the security context for authenticated users.
 */
@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    /**se
     * Filters incoming HTTP requests, extracts the JWT token, validates it,
     * and sets up the security context for the authenticated user.
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to pass the request/response along
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
        var token = recoverToken(request);
        System.out.println(token);
        if (token != null) {
            var userId = tokenProvider.validateToken(token); // Use validateToken to get user ID

            if (userId != null) {
                // Set userId as the principal without authorities
                var authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new RuntimeException("User not found based on JWT");
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header in the HTTP request.
     *
     * @param request the incoming HTTP request
     * @return the extracted JWT token, or null if the Authorization header is missing or improperly formatted
     */
    public String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String jwt = authHeader.replace("Bearer ", "");
        return jwt;
    }
}
