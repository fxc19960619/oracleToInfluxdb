<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
  
<div style="margin:0px auto; width:500px">
  
<form action="updateUser" method="post">
  
name: <input name="name" value="${u.name}"> <br>
password: <input name="password" value="${u.password}"> <br>

<input name="id" type="hidden" value="${u.id}">
<button type="submit">提交</button>
  
</form>
</div>