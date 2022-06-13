package vlad.rest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import vlad.dto.UserDto;
import vlad.model.User;
import vlad.security.jwt.JwtTokenProvider;
import vlad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    private final UserService userService;
    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    public JwtTokenProvider jwtTokenProvider;


    @ApiOperation(value = "Получить сведения о пользователе", notes = "Получить сведения о пользователе в соответствии с идентификатором URL-адреса")
    @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "Integer", paramType = "path")
    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @ApiOperation(value = "Получить сведения о пользователе", notes = "Получить сведения о авторизованном пользователе")

    @GetMapping("/me")
    public ResponseEntity<UserDto> getLoggedUser(HttpServletRequest req){

        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));

        User user = userService.findByUsername(username);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
