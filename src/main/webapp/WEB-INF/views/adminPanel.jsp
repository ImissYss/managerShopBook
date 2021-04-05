<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Panel Admin</h3>
    <a href="<c:url value="/admin/users"/> ">List User</a>
    <br/>
    <a href="<c:url value="/cat/add"/> ">add category</a>
</div>