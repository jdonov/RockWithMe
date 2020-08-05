package rockwithme.app.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rockwithme.app.exeption.NoRegisteredSkills;
import rockwithme.app.model.entity.User;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

@Aspect
@Component
public class PlayerSkillsAspect {
    private final PlayerSkillsService playerSkillsService;
    private final UserService userService;

    public PlayerSkillsAspect(PlayerSkillsService playerSkillsService, UserService userService) {
        this.playerSkillsService = playerSkillsService;
        this.userService = userService;
    }

    @Pointcut("@annotation(rockwithme.app.annotation.PlayerSkills)")
    public void checkSkills() {

    }

//    @Pointcut("execution(* rockwithme.app.web.BandRegisterController.registerBand(..))")
//    public void checkRegisteredSkills() { }


//    @Before("checkRegisteredSkills()")

    @Before("checkSkills()")
    public void check() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getUserByUsername(username);
        if (this.playerSkillsService.getByPlayerId(user.getId()).isEmpty()) {
            throw new NoRegisteredSkills("You don't have registered skills! You have to register your skills first!");
        }
    }
}
