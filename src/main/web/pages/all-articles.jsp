<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
    <c:forEach items="${catalog}" var="item" >
        <li ><p>${item.key.getName()} - </p>
            <c:forEach items="${item.value}" var="article" >
            <a href="article/${item.key.getId()}/${article.getId()}">${article.getHeader()}</a>
            </c:forEach>
        </li>
    </c:forEach>
</ul>
</body>
</html>
