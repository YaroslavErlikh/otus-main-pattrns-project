package erlikh.yaroslav.api.user

class UserAuthority {

    private final String value

    UserAuthority(String value) {
        this.value = value
    }

    String asString() {
        return value
    }
}