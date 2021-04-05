<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-10">
    <c:choose>
        <c:when test="${edit}">
            <h3 class="my-4">Edit Author</h3>
        </c:when>
        <c:otherwise>
            <h3 class="my-4">Add Author</h3>
        </c:otherwise>
    </c:choose>

    <c:if test="${added}">
        Add Author Success. View Author <a href="${pageContext.request.contextPath}/author/${authorPath}">${authorName}</a> OR <a href="${pageContext.request.contextPath}/authors/add">Add Author</a>

    </c:if>
    <c:if test="${edited}">
        Edit Author success. View Author <a href="${pageContext.request.contextPath}/author/${authorPath}">${authorName}</a>
    </c:if>
    <c:if test="${not added and not edited}">
        <div class="alert alert-danger">
            ${errorMsg} <br/>
        </div>
    </c:if>

    <form:form modelAttribute="author">
        <form:errors path="*" cssClass="alert alert-danger" element="div"/>
        <div class="form-group">
            <label for="firstName">FirstName</label>
            <form:input path="firstName" type="text" class="form-control" id="firstName" value="${author.firstName}" placeholder="FirstName"/>
            <form:errors path="firstName" cssClass="text-danger"/>
        </div>
        <div class="form-group">
            <label for="lastName">LastName</label>
            <form:input path="lastName" type="text" class="form-control" id="lastName" value="${author.lastName}" placeholder="LastName"/>
            <form:errors path="lastName" cssClass="text-danger"/>
        </div>
        <div class="form-group">
            <label for="dateOfBirth">DateOfBirth</label>
            <form:input path="dateOfBirth" type="date" class="form-control" id="dateOfBirth" value="${author.dateOfBirth}" placeholder="dateOfBirth"/>
            <form:errors path="dateOfBirth" cssClass="text-danger" />
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <form:textarea class="form-control" path="description" value="${author.description}" placeholder="Description"></form:textarea>
            <form:errors path="description" cssClass="text-danger" />
        </div>
        <div class="form-group">
            <label for="photo">Avatar</label>
            <form:input type="text" class="form-control" path="photo" id="photo" value="${author.photo}" placeholder="Avatar"/>
            <form:errors path="photo" cssClass="text-danger" />
        </div>
        <button type="submit" class="btn btn-primary">Add Author</button>


    </form:form>
</div>