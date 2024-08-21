package erlikh.yaroslav.jwt.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import erlikh.yaroslav.api.user.User
import erlikh.yaroslav.api.user.UserAuthority
import erlikh.yaroslav.api.user.UserRole
import erlikh.yaroslav.api.user.Username
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import static org.apache.commons.lang3.StringUtils.EMPTY

@Service
class JwtTokenService {

    @Value("\${jwt.secret-key}")
    private String secretKey

    @Value("\${jwt.expiration}")
    private Long expiration

    String techToken() {
        return generateToken(new User(new Username("tech-user"), new UserAuthority(UserRole.USER.name())))
    }

    String generateToken(User user) {
        return generateToken(user, Map.of())
    }

    String generateToken(User user, Map<String, String> extraClaims) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey)
        JWTCreator.Builder jwtBuilder  = JWT.create()
                .withSubject(user.getName().asString())
                .withIssuer("example")
                .withClaim("roles", user.getAuthority().asString())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
        extraClaims.forEach(jwtBuilder::withClaim)
        return jwtBuilder.sign(algorithm)
    }

    DecodedJWT decodeJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey)
        JWTVerifier verifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    <T> T getClaim(String token, ClaimsResolver<T> claimsResolver) {
        return claimsResolver.resolve(getAllClaims(token))
    }

    String getUsername(String token) {
        return getClaim(token, claims -> claims.get("sub") != null ? claims.get("sub").asString() : EMPTY)
    }

    Date getExpiration(String token) {
        return getClaim(token, claims -> claims.get("exp") != null ? claims.get("exp").asDate() : new Date())
    }

    Map<String, Claim> getAllClaims(String token) {
        return decodeJWT(token).getClaims()
    }

    boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token)
        } catch (TokenExpiredException ex) {
            return false
        }
    }

    boolean isTokenExpired(String token) {
        try {
            return decodeJWT(token).getExpiresAt().before(new Date(System.currentTimeMillis()))
        } catch (TokenExpiredException ex) {
            return true
        }
    }
}
