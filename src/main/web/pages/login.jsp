<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Вход</h1>
<form method="POST" action="login-action" enctype="application/x-www-form-urlencoded">
    <security:csrfInput/>
    <p>
        <label>
            Login:
            <input type="text" name="usernameField" value="${param['login']}">
        </label>
    </p>
    <p>
        <label>
            Password:
            <input type="password" name="passwordField">
        </label>
    </p>

    <p>
        <input type="submit" value="Login">
    </p>
</form>
</body>
</html>
