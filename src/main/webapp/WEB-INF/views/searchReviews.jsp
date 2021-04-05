<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="col-md-12">
    <h3 class="my-4">Search Review</h3>

    <form method="get" action="<c:url value="/reviews/search" />" class="form-inline">
        <label class="sr-only" for="searchTerm">Search</label>
        <input type="text" style="flex: 1;" class="form-control mb-2 mr-sm-2"
        name="searchTerm" id="searchTerm" placeholder="Search...">
        <c:if test="${not empty searchTerm}">Value=${searchTerm}</c:if>

        <button type="submit" class="btn btn-primary mb-2">Search</button>
    </form>
    <c:if test="${search}">
        <h5 class="my-4">Search: ${resultCount} Reviews</h5>

        <section class="book_review">
            <div class="review_wrap">
                <c:forEach items="${reviews}" var="review" varStatus="tagStatus">
                    <div class="review">
                        <a href="/mybooks/review/${review.id}" class="review-info">
                            <h4>${review.title}</h4>
                            <div class="description">
                                <p>${review.getShortContent()}</p>
                            </div>
                        </a>
                        <%--TODO REVIEW BOOK --%>
                    </div>
                    <div class="review-bottom">
                        <div class="review-author">User: <a href="/mybooks/user/${review.user.id}">${review.user.getDisplayName()}</a> </div>
                        <div class="review-book-info">Book: "<a href="/mybooks/book/${review.book.id}">${review.book.title}</a>"Author:


                            <%--TODO LIST AUTHORS --%>



                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
        <br/>
        <c:if test="${resultCount>0}">
            <tiles:insertAttribute name="pagination"/>
        </c:if>
    </c:if>
</div>