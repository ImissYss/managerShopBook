<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-8">
    <h3 class="my-4">Add Category</h3>
    <c:if test="${added}">
        Add Category Success. View Information <a href="${pageContext.request.contextPath}/cat/${catPath}">${catName}</a> OR <a href="${pageContext.request.contextPath}/cat/add">Add Category</a>
    </c:if>

    <c:if test="${not added}">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">
                ${errorMsg}<br/>
            </div>
        </c:if>
    </c:if>
    <form:form modelAttribute="cat">
        <form:errors path="*" cssClass="alert alert-danger" element="div"/>
        <div class="form-group">
            <label for="name">Category Name</label>
            <form:input path="name" class="form-control" id="name" type="text" value="${cat.name}" placeholder="Name"/>
            <form:errors path="name" cssClass="text-danger"/>
        </div>

        <button type="submit" class="btn btn-primary">Add category</button>

    </form:form>
</div>