package ru.effectiveMobile.socialMedia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialMediaApplication {
    //static Logger log = LogManager.getLogger(SocialMediaApplication.class);

    public static void main(String[] args) {
        //log.info("Приложение запустилось");
        SpringApplication.run(SocialMediaApplication.class, args);
    }
}
