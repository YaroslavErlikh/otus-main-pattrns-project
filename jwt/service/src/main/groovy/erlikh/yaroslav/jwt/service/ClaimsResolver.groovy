package erlikh.yaroslav.jwt.service

import com.auth0.jwt.interfaces.Claim

@FunctionalInterface
interface ClaimsResolver<T> {
    T resolve(Map<String, Claim> claims)
}
