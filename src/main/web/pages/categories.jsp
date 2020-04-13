<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <script type="text/javascript" src="scripts/categories.js"></script>
    <title>Categories</title>
</head>
<body onload="loadUserStat()">
<h1>Список категорий</h1>

<ul class="nav nav-pills nav-stacked">
    <c:forEach items="${categories}" var="category" >
        <li id="${category.getId()}">
            <a href="#">${category.getName()}
            <span class=
               <c:choose>
                 <c:when test="${category.getVisibility() eq 'PUBLIC'}">
                   "label label-success"
                 </c:when>
                 <c:when test="${category.getVisibility() eq 'PRIVATE'}">
                    "label label-warning"
                 </c:when>
               </c:choose>
            >${category.getVisibility()}</span>
            </a></li>
    </c:forEach>
</ul>
<br>
<div><a href="createCategory" class="btn btn-primary">create new category</a></div>
<br>
<h2>Статистика:</h2>
<p>Всего категорий: <span id="all">...</span></p>
<p>Всего открытых категорий: <span id="public">...</span></p>
<p>Всего приватных категорий: <span id="private">...</span></p>
<p>Всего статей во всех категориях: <span id="all-articles">...</span></p>
<p>Всего статей в открытых категориях: <span id="articles-in-public">...</span></p>
<p>Всего статей в приватных категориях: <span id="articles-in-private">...</span></p>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
</body>
</html>
