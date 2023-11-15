package org.kainos.ea.db;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.Date;
import java.security.Key;


public class AuthDao {

    private final Key hmacKey;
    Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

    public AuthDao() throws ActionFailedException {
        try {
            hmacKey = new SecretKeySpec(Base64.getDecoder().decode(System.getenv("JWT_SECRET")),
                    SignatureAlgorithm.HS256.getJcaName());
        } catch (Exception e) {
            throw new ActionFailedException("JWT_SECRET not set up correctly");
        }
    }

    public void registerUser(Connection c, Credential credential) throws ActionFailedException {
        String encodedPassword = encoder.encode(credential.getPassword());

        String insertQuery = "INSERT INTO user(email,password) VALUES(?,?)";

        try(PreparedStatement st = c.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, credential.getEmail());
            st.setString(2, encodedPassword);

            int affectedRows = st.executeUpdate();

            if (affectedRows == 0) {
                throw new ActionFailedException("Creating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("User could not be registered");
        }
    }

    public boolean validateLogin(Connection c, Credential credential) throws ActionFailedException {
        String selectQuery = "SELECT password FROM `user` WHERE email = ?";

        try(PreparedStatement st = c.prepareStatement(selectQuery)) {
            st.setString(1, credential.getEmail());

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String storedEncodedPassword = rs.getString("password");
                return encoder.matches(credential.getPassword(), storedEncodedPassword);
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Login could not be validated");
        }
    }

    public boolean deleteUser(Connection c, Credential credential) throws ActionFailedException {
        String selectQuery = "DELETE FROM user WHERE email=?;";

        try(PreparedStatement st = c.prepareStatement(selectQuery)) {
            st.setString(1, credential.getEmail());

            int affectedRows = st.executeUpdate();

            if (affectedRows == 0) {
                throw new ActionFailedException("Deleting user failed, no rows affected.");
            }

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Login could not be deleted");
        }
    }

    public String generateToken(String username) throws ActionFailedException {
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

        throw new ActionFailedException("Failed to create token");
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
