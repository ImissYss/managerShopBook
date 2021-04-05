<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<li class="nav-item active">
    <a class="nav-link" href="<c:url value="/" />">Help!
        <span class="sr-only">(current)</span>
    </a>
</li>
<li class="nav-item">
    <a class="nav-link" href="<c:url value="/login" />">Login</a>
</li>
<li class="nav-item">
    <a class="nav-link" href="<c:url value="/signup" />">Registration</a>
</li>
<c:if test="${auth.isLoggedIn()}">
<li>
    <a class="nav-link" href="<c:url value="/listCart"/> ">Your Cart</a>
</li>
</c:if>