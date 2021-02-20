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

import java.time.LocalDate;
import java.util.Date;

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
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey.secretKey())
                .compact();
    }

    /*--------------------------------- EXTRACTS TOKEN FROM REQUEST --------------------------------------*/
    public String getJwtFromRequest(String authorizationHeader) {
        if (isTokenValid(authorizationHeader)) {
            authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
            return authorizationHeader;
        }
        return "we couldn't extract token from authorization header";
    }

    /*---------------------------------- VALIDATE TOKEN -------------------------------------------*/
    public boolean isTokenValid(String authorizationHeader){
        if(!Strings.isNullOrEmpty(authorizationHeader) || authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
            return true;
        }else{
            return false;
        }
    }

    /*--------------------------------- GET USER ID FROM TOKEN -----------------------------------*/
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey.secretKey())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /*--------------------------------- CHECKS IF TOKEN IS VALID -----------------------------------*/
    public boolean isTokenTrusted(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey.secretKey()).parseClaimsJws(authToken);
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
