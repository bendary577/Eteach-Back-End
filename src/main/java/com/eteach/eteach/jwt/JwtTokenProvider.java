package com.eteach.eteach.jwt;

import com.eteach.eteach.config.security.JwtConfig;
import com.eteach.eteach.redis.RedisService;
import com.eteach.eteach.security.userdetails.ApplicationUser;
import io.jsonwebtoken.*;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private RedisService redisService;

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
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
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
    public boolean validateJwtToken(String token, HttpServletRequest request) {
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
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ApplicationUser user = (ApplicationUser) auth.getPrincipal();
            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
                allowForRefreshToken(user, request);
            } else
                request.setAttribute("exception", ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    /* ------------------------------------- INVALIDATE ACCESS TOKEN ----------------------------*/
    public void invalidateRelatedTokens(String username) {
        redisService.deleteValue(username);
    }

    /* ----------------------------- CHECK IF ACCESS TOKEN ISN'T VALID ------------------*/

    public boolean isTokenInBlacklist(String username) {
        return redisService.isAvailable(username);
    }

    /*------------------------------------- GENERATE REFRESHED TOKEN ------------------------*/

    public String generateRefreshToken(ApplicationUser user) {
        Date expiryDate = java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getRefreshExpirationDateInMs()));
        return Jwts.builder()
                .setSubject(user.getUser().getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey.secretKey())
                .compact();
    }

    /*--------------------------------- ALLOWING REFRESHING THE TOKEN -----------------------*/
    private void allowForRefreshToken(ApplicationUser user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        request.setAttribute("user", user);
    }
}
