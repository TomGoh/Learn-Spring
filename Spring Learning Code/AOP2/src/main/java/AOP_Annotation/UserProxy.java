package AOP_Annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserProxy {

    //前置通知
    @Before(value = "execution(* AOP_Annotation.User.add(..))")
    public void before(){
        System.out.println("Before...");
    }

    //后置通知
    @After(value = "execution( * AOP_Annotation.User.add(..))")
    public void after(){
        System.out.println("After...");
    }

    @AfterThrowing(value = "execution(* AOP_Annotation.User.add(..))")
    public void afterThrowing(){
        System.out.println("After throwing...");
    }

    @Around(value = "execution(* AOP_Annotation.User.add(..))")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Around before...");
        pjp.proceed();
        System.out.println("Around after...");
    }

    @AfterReturning(value = "execution(* AOP_Annotation.User.add(..))")
    public void afterReturning(){
        System.out.println("After Returning...");
    }
}
