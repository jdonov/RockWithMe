package rockwithme.app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.User;
import rockwithme.app.service.BandService;
import rockwithme.app.service.UserService;
import rockwithme.app.utils.FileUploader;

@Aspect
@Component
public class AddPhotoAspect {
    private final BandService bandService;
    private final UserService userService;

    public AddPhotoAspect(BandService bandService, UserService userService) {
        this.bandService = bandService;
        this.userService = userService;
    }

    @Pointcut("execution(* rockwithme.app.web.BandController.addPhoto(..))")
    public void addBandPhoto() {
    }

    @Pointcut("execution(* rockwithme.app.web.UserController.updateUserConfirm(..))")
    public void addUserPhoto() {
    }

    @Around("addBandPhoto()")
    public Object deleteOldBandPhoto(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String bandId = (String) proceedingJoinPoint.getArgs()[0];
        Band band = this.bandService.getBandById(bandId);
        String imgUrl = band.getImgUrl();
        Object ret = proceedingJoinPoint.proceed();
        if (imgUrl != null && band.getImgUrl() != null && !imgUrl.equals(band.getImgUrl())) {
            FileUploader.deleteFile(imgUrl);
        }
        return ret;
    }


    @Around("addUserPhoto()")
    public Object deleteOldUserPhoto(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getUserByUsername(username);
        String imgUrl = user.getImgUrl();
        UserUpdateDTO userUpdateDTO = (UserUpdateDTO) proceedingJoinPoint.getArgs()[0];
        Object ret = proceedingJoinPoint.proceed();
        if (imgUrl != null && userUpdateDTO.getImgUrl() != null && !imgUrl.equals(userUpdateDTO.getImgUrl())) {
            FileUploader.deleteFile(imgUrl);
        }
        return ret;
    }
}
