package erlikh.yaroslav.api.user

class Username {

    private final String value

    Username(String value) {
        this.value = value
    }

    String asString() {
        return value
    }
}
