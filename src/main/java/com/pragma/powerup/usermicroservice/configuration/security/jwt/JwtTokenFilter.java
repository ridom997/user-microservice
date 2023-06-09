package com.pragma.powerup.usermicroservice.configuration.security.jwt;


import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.pragma.powerup.usermicroservice.configuration.Constants.AUTHORIZATION_HEADER;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private List<String> excludedPrefixes = Arrays.asList("/auth/**", "/swagger-ui/**", "/actuator/**");
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        RequestHolder.setHttpServletRequest(req); //we assign this variable to use it in the getToken method
        String token = getToken();
        if (token != null && jwtProvider.validateToken(token)) {
            String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        for (String prefix : excludedPrefixes) {
            if (pathMatcher.matchStart(prefix, currentRoute)) {
                return true;
            }
        }
        return false;
    }

    public static String getToken() {
        String header = RequestHolder.getHttpServletRequest().getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // return everything after "Bearer "
        }
        return null;
    }

    class RequestHolder{

        private RequestHolder(){}
        private static HttpServletRequest httpServletRequest;

        public static HttpServletRequest getHttpServletRequest() {
            return httpServletRequest;
        }

        public static void setHttpServletRequest(HttpServletRequest httpServletRequest) {
            RequestHolder.httpServletRequest = httpServletRequest;
        }
    }
}
