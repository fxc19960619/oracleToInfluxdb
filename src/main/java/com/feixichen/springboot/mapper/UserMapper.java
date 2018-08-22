package com.feixichen.springboot.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.feixichen.springboot.pojo.User;

@Mapper
public interface UserMapper {
	 @Select("select * from ${tableName} ")
	    List<Object> findAll(@Param("tableName") String tableName);
	     
	    @Insert(" insert into test_user (name,password) values (#{name},#{password}) ")
	    public int save(User user); 
	     
	    @Delete(" delete from test_user where id= #{id} ")
	    public void delete(int id); 
	
	    @Select("select * from test_user where id= #{id} ")
	    public abstract User get(int id);
	       
	    @Update("update test_user set name=#{name} password=#{password} where id=#{id} ")
	    public int update(User ser); 
	    
	    @Select("select * from ${tableName}")
	    List<HashMap<String, Object>> findInfo(@Param("tableName") String tableName);
	    
	    @Select("select a.column_name from user_cons_columns a, user_constraints b where a.constraint_name = b.constraint_name and b.constraint_type = 'P' and a.table_name = 'TEST_USER' ")
	    public Object getMainId(@Param("tableName") String tableName);

		
	    
}