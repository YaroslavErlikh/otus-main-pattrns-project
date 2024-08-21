package erlikh.yaroslav.common.test

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import erlikh.yaroslav.api.user.UserRole
import io.mockk.*

class TestHelper {

    static void verifyOnce(MockKVerificationScope action) {
        MockKKt.verify(1, action)
    }

    static void verifyNotCalled(MockKVerificationScope action) {
        MockKKt.verify(0, action)
    }

    static void verifyOnce(WireMockServer server, RequestPatternBuilder requestPatternBuilder) {
        server.verify(1, requestPatternBuilder)
    }

    static void verifyNotCalled(WireMockServer server, RequestPatternBuilder requestPatternBuilder) {
        server.verify(0, requestPatternBuilder)
    }

    static final String token = JWT.create()
    .withSubject("user")
    .withIssuer("issuer")
    .withClaim("roles", UserRole.USER.name)
    .withIssuedAt(new Date(System.currentTimeMillis()))
    .withExpiresAt(new Date(System.currentTimeMillis() + 60000))
    .sign(Algorithm.HMAC256("secret"))

    static final String adminToken = JWT.create()
    .withSubject("admin")
    .withIssuer("issuer")
    .withClaim("roles", UserRole.ADMIN.name)
    .withIssuedAt(new Date(System.currentTimeMillis()))
    .withExpiresAt(new Date(System.currentTimeMillis() + 60000))
    .sign(Algorithm.HMAC256("secret"))

    static final String techToken = JWT.create()
    .withSubject("tech-user")
    .withIssuer("issuer")
    .withClaim("roles", UserRole.USER.name)
    .withIssuedAt(new Date(System.currentTimeMillis()))
    .withExpiresAt(new Date(System.currentTimeMillis() + 60000))
    .sign(Algorithm.HMAC256("secret"))
}
