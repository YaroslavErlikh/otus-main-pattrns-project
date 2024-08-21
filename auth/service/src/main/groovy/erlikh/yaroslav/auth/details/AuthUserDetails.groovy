package erlikh.yaroslav.auth.details

import erlikh.yaroslav.api.user.UserRole
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthUserDetails implements UserDetails {

    private final String name
    private final String login
    private final String password
    private final UserRole role

    AuthUserDetails(String name, String login, String password, UserRole role) {
        this.name = name
        this.login = login
        this.password = password
        this.role = role
    }

    @Override
    List getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role?.name))
    }

    @Override
    String getUsername() {
        return login
    }

    @Override
    String getPassword() {
        return password
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        true
    }
}