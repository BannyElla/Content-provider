<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="form" type="org.levelup.web.RegistrationForm" scope="request"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<jsp:useBean id="verifiedUserName" type="java.lang.String" scope="session" />--%>

<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Форма регистрации</h1>
<form:form modelAttribute="form" action="registration" method="post" enctype="application/x-www-form-urlencoded">
    <p>
        <label>
            Login:
            <form:input type="text" path="login"/>
        </label>
        <form:errors path="login" cssStyle="color: red"/>
    </p>
    <p>
        <label>
            Password:
            <form:input type="password" path="password"/>
        </label>
        <form:errors path="password" cssStyle="color: red"/>
    </p>

    <p>
        <input type="submit" value="Sign in">
    </p>
</form:form>
</body>
</html>
