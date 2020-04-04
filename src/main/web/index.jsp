<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Шпаргалка Java</title>
</head>
<body>
<h1>Шпаргалка Java</h1>
<c:if test="${not empty sessionScope['verifiedUserName']}">
    <p>
        Привет, ${sessionScope['verifiedUserName']}!
    </p>
</c:if>
<div><a href="#">Основы Java</a></div>
<div><a href="#">Типы данных</a></div>
<div><a href="#">Методы и инкапсуляция</a></div>
<div><a href="#">Условия и циклы</a></div>

<c:choose>
    <c:when test="${empty sessionScope['verifiedUserName']}">
        <br>
        <p>
            <a href="login">Войдите</a> или <a href="registration">зарегистрируйтесь</a>, чтобы получить доступ к закрытым разделам:
        </p>
        <div style="color: gray">Дженерики</div>
        <div style="color: gray">Потоки ввода/вывода</div>
        <div style="color: gray">Многопоточность</div>
    </c:when>
    <c:otherwise>
        <div><a href="#">Дженерики</a></div>
        <div><a href="#">Потоки ввода/вывода</a></div>
        <div><a href="#">Многопоточность</a></div>
    </c:otherwise>
</c:choose>
<br>
<c:if test="${not empty sessionScope['message']}">
    <p>
        отладочное сообщение: ${sessionScope['message']}
    </p>
</c:if>
</body>
</html>
