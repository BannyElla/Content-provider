<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="form" type="org.levelup.web.RegistrationForm" scope="request" />
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Форма регистрации</h1>
<form method="POST" action="registration" enctype="application/x-www-form-urlencoded">
    <p>
        <label>
            Login:
            <input type="text" name="usernameField" value="${param['login']}">
        </label>
    </p>
    <p>
        <label>
            Password:
            <input type="password" name="passwordField" value=""${form.password}"">
        </label>
    </p>

    <p>
        <input type="submit" value="Sign in">
    </p>
</body>
</html>
