package erlikh.yaroslav.editor.mapper


import org.springframework.http.ResponseEntity

class ResponseEntityConverter {

    static <T> ResponseEntity<T> toResponseEntity(T value) {
        if (value == null) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(value)
        }
    }
}
