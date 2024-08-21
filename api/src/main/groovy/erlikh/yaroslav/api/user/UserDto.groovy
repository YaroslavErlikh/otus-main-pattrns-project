package erlikh.yaroslav.api.user

record UserDto(
        String name,
        String login,
        String password,
        UserRole role
) {

    String getName() {
        return name
    }

    String getLogin() {
        return login
    }

    String getPassword() {
        return password
    }

    UserRole getRole() {
        return role
    }
}