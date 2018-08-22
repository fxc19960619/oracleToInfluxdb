package com.feixichen.springboot.web;
import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class HelloController {
 
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	
	@RequestMapping("/hello")
    public String hello(Model m) {
        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        
        logger.error("error");
        logger.info("info");
        logger.debug("debug");
  
        return "hello";
    }
}