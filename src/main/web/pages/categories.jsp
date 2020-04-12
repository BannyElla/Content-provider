<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Categories</title>
</head>
<body>
<c:forEach items="${categories}" var="category" >
    <div id="${category.getId()}">${category.getName()}</div>
</c:forEach>
</body>
</html>
