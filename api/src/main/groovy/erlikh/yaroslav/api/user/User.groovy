package erlikh.yaroslav.api.user

class User {

    private final Username name
    private final UserAuthority authority

    User(Username name, UserAuthority authority) {
        this.name = name
        this.authority = authority
    }

    Username getName() {
        return name
    }

    UserAuthority getAuthority() {
        return authority
    }
}