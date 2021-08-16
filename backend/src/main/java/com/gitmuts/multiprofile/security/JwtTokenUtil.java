package com.gitmuts.multiprofile.security;

import com.gitmuts.multiprofile.user.entity.Organization;
import com.gitmuts.multiprofile.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;
import static com.gitmuts.multiprofile.model.Constants.*;



@Component
@Slf4j
public class JwtTokenUtil implements Serializable {
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try{
            return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e){
            log.error("{}", e.getMessage());
            return null;
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user, Organization organization) {
        log.info("Generating token for {}", user.getUsername()+"_"+organization.getId());
        return doGenerateToken(user.getUsername()+"_"+organization.getId());
    }

    private String doGenerateToken(String subject) {

        final Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder().setClaims(claims).setIssuer("https://lipachat.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token).split("_")[0];
        log.info("Username t{}t user details username t{}t", username, userDetails.getUsername());
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String getUsername(String token) {
        final String username = Jwts.parser().setSigningKey(SIGNING_KEY).setAllowedClockSkewSeconds(5 * 60 * 60)
                .parseClaimsJws(token).getBody().getSubject();
        try {
            Jwts.parser().setSigningKey(SIGNING_KEY).setAllowedClockSkewSeconds(0).parseClaimsJws(token).getBody()
                    .getSubject();
        } catch (final Exception e) {

        }
        return username;
    }

    public String getUsernameUnlimitedSkew(String token) {
        String username=null;
        try {
            username = Jwts.parser().setSigningKey(SIGNING_KEY).setAllowedClockSkewSeconds(Integer.MAX_VALUE)
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        return username;
    }
}
