package erlikh.yaroslav.user.exceptions

class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String message) {
        super(message)
    }
}
