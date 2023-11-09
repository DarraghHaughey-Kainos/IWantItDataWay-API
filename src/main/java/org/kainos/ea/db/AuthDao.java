package org.kainos.ea.db;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.Date;
import java.security.Key;


public class AuthDao {

    private final Key hmacKey;
    Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);

    public AuthDao() throws AuthenticationException {
        try {
            hmacKey = new SecretKeySpec(Base64.getDecoder().decode(System.getenv("JWT_SECRET")),
                    SignatureAlgorithm.HS256.getJcaName());
        } catch(Exception e) {
            throw new AuthenticationException("JWT_SECRET not set up correctly");
        }
    }

    public boolean validateLogin(Credential credential) throws AuthenticationException, ActionFailedException {
        try (Connection c = DatabaseConnector.getConnection()) {
            assert c != null;

            String selectQuery = "SELECT password FROM `user` WHERE username = ?";
            PreparedStatement st = c.prepareStatement(selectQuery);

            st.setString(1, credential.getUsername());

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String storedEncodedPassword = rs.getString("password");
                return encoder.matches(credential.getPassword(), storedEncodedPassword);
            }
            return false;
        } catch (SQLException e) {
            throw new ActionFailedException(e.getMessage());
        }
    }


    public boolean registerUser(Credential credential) throws ActionFailedException, AuthenticationException {
        try (Connection c = DatabaseConnector.getConnection()) {
            assert c != null;

            String encodedPassword = encoder.encode( credential.getPassword());

            String insertQuery = "INSERT INTO user(username,password) VALUES(?,?)";
            PreparedStatement st = c.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, credential.getUsername());
            st.setString(2, encodedPassword);

            int affectedRows = st.executeUpdate();

            if (affectedRows > 0) {
                return true;
            }

            throw new AuthenticationException("Failed to register user");
        } catch (SQLException e) {
            throw new ActionFailedException(e.getMessage());
        }
    }

    public String generateToken(String username) throws AuthenticationException {
        Date currentDate = new Date();

        String token = Jwts.builder()
                .claim("username", username)
                .setIssuedAt(currentDate)
                .setExpiration(DateUtils.addHours(currentDate, 1))
                .signWith(hmacKey)
                .compact();

        if (token != null) {
            return token;
        }

        throw new AuthenticationException("Failed to create token");
    }

    public Claims parseToken(String tokenString) throws AuthenticationException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(tokenString)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("Token has expired");
        } catch (MalformedJwtException e) {
            throw new AuthenticationException("Invalid token");
        }
    }

}
