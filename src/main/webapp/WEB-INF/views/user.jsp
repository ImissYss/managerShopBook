<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Information User: ${user.getDisplayName()} <br/>

        FirstName : ${user.firstName} <br/>
        LastName : ${user.lastName} <br/>
        DisplayName : ${user.displayName} <br/>
        Email : ${user.email} <br/>


        <br/>

        Description : ${user.description} <br/>

        <c:choose>
            <c:when test="${auth.getUserInfo().isAdminOrModerator()}">
                <a href="/mybooks/user/${user.id}/edit">Edit</a>
            </c:when>
            <c:when test="${auth.getUserInfo().user.id == user.id}">
                <a href="/mybooks/user/${user.id}/edit">Edit</a>
            </c:when>
        </c:choose>
        <br/>
        <c:if test="${auth.getUserInfo().user.id == user.id}">
            <a href="/mybooks/user/change-password">Change password</a>
        </c:if>
        <br/>
    </h3>
</div>