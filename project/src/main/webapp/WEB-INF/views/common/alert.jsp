<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script>
	alert('${msg}'); //스프링에서 넣어준 EL
	<c:if test="${!empty url}">
		location.href='${url}';
	</c:if>
	<c:if test="${empty url}">
		history.back(); //저장 실패 후 내용이 그대로 남아있게 됨
	</c:if>
	
</script>