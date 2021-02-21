package com.eteach.eteach.jwt;

import com.eteach.eteach.config.JwtConfig;
import com.eteach.eteach.security.userdetails.ApplicationUser;
import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final JwtConfig jwtConfig;
    private final JwtSecretKey jwtSecretKey;

    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig, JwtSecretKey jwtSecretKey){
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
    }

    /*--------------------------------- GENERATES A NEW TOKEN -----------------------------------*/
    public String generateToken(Authentication authentication) {

        ApplicationUser applicationUser = (ApplicationUser) authentication.getPrincipal();
        Date expiryDate = java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()));

        //jwt subject will contain [username, authorities]
        return Jwts.builder()
                .setSubject(applicationUser.getUser().getUsername())
                .claim("authorities", applicationUser.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey.secretKey())
                .compact();
    }

    /*--------------------------------- EXTRACTS TOKEN FROM REQUEST --------------------------------------*/
    public String getJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        System.out.println("auth header is :" + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("auth header starts with bearer");
            return authHeader.replace("Bearer ", "");
        }else{
            return null;
        }
    }

    /* ------------------------------ GET USERNAME FROM TOKEN-------------------------------------------------*/
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey.secretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    /* ------------------------------ GET AUTHORITIES FROM TOKEN-------------------------------------------------*/
    public List<Map<String, String>> getAuthoritiesFromJwtToken(String token) {
        List<Map<String, String>> authorities = (List<Map<String, String>>) Jwts.parser().setSigningKey(jwtSecretKey.secretKey()).parseClaimsJws(token).getBody().get("authorities");
        return authorities;
    }

    /*------------------------------ CHECKS IF TOKEN IS EXPIRED -------------------------------------*/
    /*
    private Boolean isTokenExpired(String token) {
        final Date expiration = jwtConfig.getTokenExpirationAfterDays();
        return expiration.before(new Date());
    }
    */
    /*--------------------------------- CHECKS IF TOKEN IS VALID -----------------------------------*/
    public boolean validateJwtToken(String token) {
        try {
            Jws<Claims> jwtClaims = Jwts.parser().setSigningKey(jwtSecretKey.secretKey()).parseClaimsJws(token);
            System.out.println("claims :" + jwtClaims);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
