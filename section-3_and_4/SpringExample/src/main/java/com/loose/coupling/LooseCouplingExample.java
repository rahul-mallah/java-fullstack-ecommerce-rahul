package com.loose.coupling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LooseCouplingExample {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationLooseCouplingBeanContext.xml");
        UserManager userManager = (UserManager) context.getBean("myUserManager");
        System.out.println(userManager.getUserInfo());

        ApplicationContext context2 = new ClassPathXmlApplicationContext("applicationLooseCouplingBeanContext.xml");
        UserManager userManager2 = (UserManager) context2.getBean("myUserManagerWS");
        System.out.println(userManager2.getUserInfo());

        ApplicationContext context3 = new ClassPathXmlApplicationContext("applicationLooseCouplingBeanContext.xml");
        UserManager userManager3 = (UserManager) context3.getBean("myUserManagerNew");
        System.out.println(userManager3.getUserInfo());
    }
}
