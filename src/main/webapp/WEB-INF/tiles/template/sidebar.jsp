<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<c:if test="${auth.isLoggedIn()}">
    <div class="card my-4">
        <h5 class="card-header">User panel</h5>
        <div class="card-body">
            <ul class="list-unstyled mb-0">
                <li>
                    <a href="<c:url value="/user/${auth.getUserInfo().getUser().id}"/> ">${auth.getUserInfo().getUser().getDisplayName()}</a>
                </li>
                <li>
                    <a href="<c:url value="/logout"/> ">Log out</a>
                </li>
            </ul>
        </div>
    </div>
</c:if>
<c:if test="${auth.getUserInfo().isAdmin()}">
    <div class="card md-4">
        <h5 class="card-header">Admin panel</h5>
        <div class="card-body">
            <a href="<c:url value="/admin"/> ">Panel Admin</a>
            <ul class="sidebar-list list-unstyled md-0">
                <li>
                    <a href="<c:url value="/admin/users"/>">List User</a>
                </li>
                <li>
                    <a href="<c:url value="/cat/add"/> ">Add category</a>
                </li>
            </ul>
        </div>
    </div>

</c:if>

<div class="card my-4">
    <h5 class="card-header">Menu</h5>
    <div class="card-body">
        <ul class="sidebar-list list-unstyled mb-0">
            <c:if test="${!auth.isLoggedIn()}">
                <li>
                    <a href="<c:url value="/login" />">Login</a>
                </li>
                <li>
                    <a href="<c:url value="/signup"/> ">Registration</a>
                </li>
            </c:if>
            <li>
                <a href="<c:url value="/books" />">Book</a>
                <c:if test="${auth.isLoggedIn() and auth.getUserInfo().isAdmin()}">
                    <ul>
                        <li>
                            <a href="<c:url value="/books/add"/> ">Add Book</a>
                        </li>
                    </ul>
                </c:if>
            </li>
            <li>
                <a href="<c:url value="/reviews" />">Review</a>
            </li>
            <li>
                <a href="<c:url value="/authors" />">Author</a>
                <c:if test="${auth.isLoggedIn() and auth.getUserInfo().isAdmin()}">
                    <ul>
                        <li>
                            <A href="<c:url value="/authors/add" />">Add Author</A>
                        </li>
                    </ul>
                </c:if>
            </li>
            <li>
                <a href="<c:url value="/books/search"/> ">Search Book</a>
            </li>
            <li>
                <a href="<c:url value="/authors/search" />">Search Author</a>
            </li>
            <li>
                <a href="<c:url value="/reviews/search" />">Search Review</a>
            </li>
        </ul>
    </div>
</div>