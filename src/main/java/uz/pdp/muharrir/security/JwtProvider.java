package uz.pdp.muharrir.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.jdbc.Expectation;
import org.springframework.stereotype.Component;
import uz.pdp.muharrir.entity.Role;

import java.util.Date;

@Component
public class JwtProvider {
    private static final long expireTime = 1000 * 60 * 60 * 24l;
    private static final String secretKet = "secretKeyWord";

    public String generateToken(String username, Role roles) {
        Date expireDate = new Date(System.currentTimeMillis() + JwtProvider.expireTime);
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .claim("roles", roles.getName())
                .signWith(SignatureAlgorithm.HS512, secretKet)
                .compact();


    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKet)
                    .parseClaimsJws(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            String email = Jwts
                    .parser()
                    .setSigningKey(secretKet)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return email;
        } catch (Exception e) {
            return null;
        }
    }
}
