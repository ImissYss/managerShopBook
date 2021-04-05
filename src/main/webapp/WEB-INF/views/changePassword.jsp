<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-10">
    <h3 class="my-4">
        Change Password
    </h3>
    <c:if test="${changed}">
        Changed. <a href="${pageContext.request.contextPath}/user/${auth.getUserInfo().user.id}">Exit</a>
    </c:if>

    <c:if test="${not changed}">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">
                ${errorMsg} <br/>
            </div>
        </c:if>

        <form method="post">
            <div class="form-group">
                <label for="oldPassword">Old Password</label>
                <input type="password" class="form-group" name="oldPassword" id="oldPassword">
            </div>
            <div class="form-group">
                <label for="newPassword">New Password</label>
                <input type="password" class="form-group" name="newPassword" id="newPassword">
            </div>
            <div class="form-group">
                <label for="newPassword2">New Password One</label>
                <input type="password" class="form-control" name="newPassword2" id="newPassword2" value="" placeholder=""/>
            </div>
            <button type="submit" class="btn btn-primary">ChangePassword</button>
        </form>
    </c:if>
</div>