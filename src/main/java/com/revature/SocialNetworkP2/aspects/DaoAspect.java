package com.revature.SocialNetworkP2.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DaoAspect {
    Logger logger = Logger.getLogger(DaoAspect.class);

    @AfterThrowing(value = "execution(* findBy*(..))", throwing = "e")
    public void afterFindingById(Exception e){
        logger.warn("exception thrown when finding by id", e);
    }

    @AfterThrowing(value = "execution(* findAll*(..))", throwing = "e")
    public void afterFindingAll(Exception e){
        logger.warn("exception thrown when finding all", e);
    }
}
