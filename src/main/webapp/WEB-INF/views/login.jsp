<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-10">
    <h3 class="my-4">Login</h3>
    <c:if test="${not empty errorMsg}">
        <div class="alert alert-danger">
            ${errorMsg}<br/>
        </div>
    </c:if>
    <form action="" method="post">
        <div class="form-group">
            <label for="exampleInputEmail1">Email</label>
            <input type="email" name="username" class="form-control" id="exampleInputEmail1" placeholder="Email">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-primary">Login</button>

    </form>
</div>