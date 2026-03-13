package cs.ubb.hrelperbe.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenProvider {

    // The secret key used for signing and verifying JWT tokens. This value is injected from application properties.
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    /**
     * Generates a JWT access token for a given user.
     * This token will be signed using a secret key and will contain the user's ID as the subject.
     *
     * @param user the user for whom the token is being generated
     * @return a signed JWT token containing the user's ID
     */
//    public String generateAccessToken(User user) {
//        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
//        return JWT.create()
//                .withSubject(user.getUserId().toString())
//                .sign(algorithm);
//    }

    /**
     * Validates a JWT token by verifying its signature and extracting the user ID from the token's subject.
     * If the token is valid, it returns the user's ID. If invalid, it throws a JwtValidationException.
     *
     * @param token the JWT token to validate
     * @return the user ID extracted from the token's subject if valid
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error while validating token");
        }
    }

}