package erlikh.yaroslav.user.controller

import erlikh.yaroslav.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController {

    @Autowired
    private UserService userService

    @GetMapping
    ResponseEntity findByLogin(@RequestParam(value = "login", required = true) String login) {
        return ResponseEntity.ok(userService.findByLogin(login))
    }
}
