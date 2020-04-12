<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="categoriesForm" type="org.levelup.web.forms.CategoriesForm" scope="request"/>
<jsp:useBean id="visibility" type="org.levelup.web.forms.VisibilityList" scope="request"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Create category</title>
</head>
<body>
<h1>Форма создания категории</h1>
<form:form modelAttribute="categoriesForm" action="createCategory" method="post" enctype="application/x-www-form-urlencoded">
    <p>
        <label>
            Name:
            <form:input type="text" path="name"/>
        </label>
        <form:errors path="name" cssStyle="color: red"/>
    </p>
    <p>
        <label>
        Visibility:
        <form:select path="visible">
            <c:forEach items="${visibility.visibility}" var="visible">
                <option value="${visible.name()}">${visible.name()}</option>
            </c:forEach>
        </form:select>
        </label>
    </p>

    <p>
        <input type="submit" value="Create">
    </p>
</form:form>
</body>
</html>
