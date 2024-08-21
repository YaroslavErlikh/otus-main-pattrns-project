package erlikh.yaroslav.editor.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class EditorAuthenticationToken extends AbstractAuthenticationToken {

    private String token
    Collection<GrantedAuthority> authorities

    EditorAuthenticationToken(String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities)
        this.token = token
        this.authorities = authorities
    }

    @Override
    Object getCredentials() {
        return token
    }

    @Override
    Object getPrincipal() {
        return token
    }
}
