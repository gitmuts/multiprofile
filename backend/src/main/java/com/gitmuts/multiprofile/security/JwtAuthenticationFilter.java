package com.gitmuts.multiprofile.security;

import com.gitmuts.multiprofile.user.entity.Organization;
import com.gitmuts.multiprofile.user.entity.User;
import com.gitmuts.multiprofile.user.model.UserSession;
import com.gitmuts.multiprofile.user.repo.OrganizationRepo;
import com.gitmuts.multiprofile.user.repo.UserSessionRepo;
import com.gitmuts.multiprofile.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gitmuts.multiprofile.model.Constants.*;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Autowired
    UserSessionRepo userSessionRepo;
    @Autowired
    OrganizationRepo organizationRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final String header = req.getHeader(HEADER_STRING);
        logger.info("Header "+ header+ " request "+ req.getRequestURI());
        String usernameWithOrgId = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            if(authToken != null && authToken !="") {
                try {
                    usernameWithOrgId = jwtTokenUtil.getUsernameFromToken(authToken);
                } catch (final IllegalArgumentException e) {
                    logger.error("an error occurred during getting username from token {}", e.getCause());
                } catch (final ExpiredJwtException e) {
                    logger.info("User token has expired");
                    usernameWithOrgId = jwtTokenUtil.getUsernameUnlimitedSkew(authToken);
                    final User user = userService.findByUsername(usernameWithOrgId.split("_")[0]);
                    UserSession userSession = userSessionRepo.findByUser(user);
                    userSession.setLoggedIn(0);
                    userSessionRepo.save(userSession);
                    usernameWithOrgId = null;
                } catch (final SignatureException e) {
                    logger.error("SignatureException. {}", e.getCause());
                }
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        logger.info("Username found "+ usernameWithOrgId);

        if(SecurityContextHolder.getContext().getAuthentication() != null){
            logger.info("Security context handler is not null");
        }

        if (usernameWithOrgId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            String username = usernameWithOrgId.split("_")[0];
            logger.info("Username extracted "+ username);

            long organizationId = Long.parseLong(usernameWithOrgId.split("_")[1]);

            final User userDetails = (User) userDetailsService.loadUserByUsername(username);

            logger.info("User details "+ userDetails.toString());

            Optional<Organization> optionalOrganization= organizationRepo.findById(organizationId);

            if(optionalOrganization.isPresent()){
                userDetails.setSelectedOrganization(optionalOrganization.get());
            } else{
                logger.warn("Organization with id "+ organizationId + " not found!");
            }

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                // Role role = roleService.getUserRole(userDetails.getUsername());
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.warn("Token not valid");
            }
        }

        chain.doFilter(req, res);
    }

    private List<SimpleGrantedAuthority> getAuthority() {

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        return authorities;
    }
}
