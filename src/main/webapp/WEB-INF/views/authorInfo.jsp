<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Author: ${author.getDisplayName()}</h3>

    FirstName: ${author.firstName}<br/>
    LastName: ${author.lastName}<br/>

    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        <a href="/mybooks/authors/edit/${author.id}">Edit Author</a>
    </c:if>
    <br/>
    <br/>
     Book: <br/>
    <section class="books">
        <div class="book_main_list">

            <c:forEach items="${author.books}" var="book" varStatus="tagStatus">
                <div class="book">
                    <a href="/mybooks/book/${book.id}">
                        <img class="cover" alt="images" src="${pageContext.request.contextPath}/theme${book.cover}"/>
                        <div class="info">
                            <h5>${book.title}</h5>
                            <div class="author"><c:forEach items="${book.authors}" var="author" varStatus="status" ><c:if test="${status.index==0}">${author.getDisplayName()}</c:if> </c:forEach>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>
</div>