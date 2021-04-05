<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-10">
    <h3 class="my-4">Edit User: ${userName}</h3>
    <c:if test="${edited}">
        Information. View <a href="${pageContext.request.contextPath}/user/${userPath}">${userName}</a>
    </c:if>

    <c:if test="${not edited}">
        <div class="alert alert-danger">
            ${errorMsg} <br/>
        </div>
    </c:if>

    <form:form modelAttribute="user">
        <form:errors path="*" cssClass="alert alert-danger" element="div"/>
        <div class="form-group">
            <label for="firstName">FirstName</label>
            <form:input path="firstName" type="text" class="form-control" id="firstName" value="${user.firstName}"/>
            <form:errors path="firstName" cssClass="text-danger"/>
        </div>
        <div class="form-group">
            <label for="lastname">LastName</label>
            <form:input type="text" class="form-control" path="lastName" id="lastname" value="${user.lastName}" placeholder="LastName"/>
            <form:errors path="lastName" cssClass="text-danger" />
        </div>
        <div class="form-group">
            <label for="displayName">DisplayName</label>
            <form:input type="text" class="form-control" path="displayName" id="displayName" value="${user.displayName}" placeholder="DisplayName"/>
            <form:errors path="displayName" cssClass="text-danger" />
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <form:textarea class="form-control" path="description" value="${user.description}" placeholder="Opis"></form:textarea>
            <form:errors path="description" cssClass="text-danger" />
        </div>
        <button type="submit" class="btn btn-primary">Edit</button>
    </form:form>
</div>