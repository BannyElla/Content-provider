<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Шпаргалка Java</title>
</head>
<body>
<h1>Шпаргалка Java</h1>

<c:forEach var="category" items="${public_categories}">
    <div>
        <a href="category/${category.getId()}">${category.getName()}</a>
    </div>
</c:forEach>
<c:choose>
    <c:when test="${empty username}">
        <br>
        <p>
            <a href="login">Войдите</a> или <a href="registration">зарегистрируйтесь</a>, чтобы получить доступ к
            закрытым разделам:
        </p>
        <c:forEach var="category" items="${private_categories}">
            <div> ${category.getName()}</div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:forEach var="category" items="${private_categories}">
            <div>
                <a href="private-category/${category.getId()}">${category.getName()}</a>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>
<br>
</body>
</html>
