<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<form:form modelAttribute="addressInfo">
    <div class="form-group">
        <label for="username">username</label>
        <form:input path="username" type="text" class="form-control" id="username" value="${addressInfo.username}" placeholder="username"/>
        <form:errors path="username" cssClass="text-danger"/>
    </div>
    <div class="form-group">
        <label for="address">Address</label>
        <form:input path="address" type="text" class="form-control" id="address" value="${addressInfo.address}" placeholder="address"/>
        <form:errors path="address" cssClass="text-danger"/>
    </div>
    <div class="form-group">
        <label for="phone">Phone</label>
        <form:input path="phone" type="text" class="form-control" id="phone" value="${addressInfo.phone}" placeholder="phone"/>
        <form:errors path="phone" cssClass="text-danger"/>
    </div>
    <button type="submit" class="btn btn-primary">Save</button>

</form:form>