package com.feixichen.springboot.web;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.influxdb.InfluxDB;
import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.feixichen.influxdb.InfluxDBTest;
import com.feixichen.springboot.mapper.UserMapper;
import com.feixichen.springboot.pojo.User;

import org.slf4j.LoggerFactory;

@Controller
@PropertySource("classpath:application.properties")//如果是application.properties，就不用写@PropertyScource("application.properties")，其他名字用些  
public class UserController {
	
	  @Autowired UserMapper userMapper;
	  
	  private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	  
      @Value("${influxdb.url}")
	    private String url;
      
      @Value("${oracle.tableName}")
        private String tableName;
      
      @Value("${influxdb.username}")
	    private String username;
     
      @Value("${influxdb.password}")
	    private String password;
      
     @Value("${influxdb.database}")
	    private String database;
     
     @Value("${influxdb.duration}")
        private String duration;
      
     @Value("${influxdb.policyName}")
	    private String policyName;
     
     @Value("${influxdb.tablename}")
        private String influxdbTableName;
	  	  
	  @RequestMapping("/addUser")
	    public String listUser(User u) throws Exception {
	        userMapper.save(u);
	        return "redirect:listUser";
	    }
	    @RequestMapping("/deleteUser")
	    public String deleteUser(User u) throws Exception {
	        userMapper.delete(u.getId());
	        return "redirect:listUser";
	    }
	    @RequestMapping("/updateUser")
	    public String updateUser(User u) throws Exception {
	        userMapper.update(u);
	        return "redirect:listUser";
	    }
	    @RequestMapping("/editUser")
	    public String listUser(int id,Model m) throws Exception {
	        User u= userMapper.get(id);
	        m.addAttribute("u", u);
	        return "editUser";
	    }
	  
     
	  @SuppressWarnings("rawtypes")
	  @RequestMapping("/list")
	    public String listUser(Model m) throws Exception {
		  	
		
		  	List<HashMap<String,Object>> lo = userMapper.findInfo(tableName);
		  	logger.info("oracle数据库信息：" + lo.toString());
		  	
		  	Object mianID = userMapper.getMainId(tableName);		  	
			logger.info("oracle数据库主键：" + mianID.toString());
					  		  	   	        
		  	
		  	InfluxDBTest influx = new InfluxDBTest("http://127.0.0.1:8086")
					.setDatabase(database, policyName)
			        .setAuthentication(username, password)
			        .setRetentionPolicy(duration, 1)
			        .build();
		    logger.info("连接成功");
		  	
		  	for(HashMap<String, Object> hs :lo)	{
		  		
		  		Map<String, String> tags = new HashMap<String, String>();
				Map<String, Object> fields = new HashMap<String, Object>();
				
				tags.put(mianID.toString(), hs.get(mianID).toString());
				
				fields.putAll(hs);
		  		influx.insert(influxdbTableName, tags, fields);
		  		logger.info("数据存储成功");
				}
		  	
		  	return "listUser";
	    }
}