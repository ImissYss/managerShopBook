<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-10">
    <h3 class="my-4">Registration</h3>

    <c:if test="${added}">
        Registration Success.<a href="${pageContext.request.contextPath}/login">Login</a>
    </c:if>
    <c:if test="${not added}">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">
                ${errorMsg}<br/>
            </div>
        </c:if>

        <form:form modelAttribute="user">
            <form:errors path="*" cssClass="alert alert-danger" element="div"/>
            <div class="form-group">
                <label for="email">Email</label>
                <form:input path="email" type="email" class="form-control" id="email" value="${user.email}" placeholder="Email"/>
                <form:errors path="email" cssClass="text-danger"/>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <form:input path="password" type="password" class="form-control" id="password" value="${user.password}" placeholder="Password"/>
                <form:errors path="password" cssClass="text-danger"/>
            </div>
            <div class="form-group">
                <label for="matchingPassword">MatchingPassword</label>
                <form:input path="matchingPassword" type="password" class="form-control" id="matchingPassword" value="${user.matchingPassword}" placeholder="MatchingPassword"/>
                <form:errors path="matchingPassword" cssClass="text-danger"/>
            </div>
            <div class="form-group">
                <label for="name">Name</label>
                <form:input path="name" type="name" class="form-control" id="name" value="${user.name}" placeholder="Name"/>
                <form:errors path="name" cssClass="text-danger"/>
            </div>
            <div class="form-group">
                <label for="lastName">LastName</label>
                <form:input path="lastName" type="text" class="form-control" id="lastName" value="${user.lastName}" placeholder="LastName"/>
                <form:errors path="lastName" cssClass="text-danger"/>
            </div>

            <div class="form-group">
                <label for="address">Address</label>
                <form:input path="address" type="text" class="form-control" id="address" value="${user.address}" placeholder="address"/>
                <form:errors path="address" cssClass="text-danger"/>
            </div>
            <div class="form-group">
                <label for="phone">phone</label>
                <form:input path="phone" type="text" class="form-control" id="phone" value="${user.phone}" placeholder="phone"/>
                <form:errors path="phone" cssClass="text-danger"/>
            </div>

            <button type="submit" class="btn btn-primary">Registration</button>

        </form:form>
    </c:if>
</div>