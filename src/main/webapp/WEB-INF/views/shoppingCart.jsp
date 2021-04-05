<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>


<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-10 col-md-offset-1">
            <table class="table table-hover">

                <thead>
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th class="text-center">Price</th>
                    <th class="text-center">Total</th>
                    <th> </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${books}" var="book" varStatus="tagStatus">
                <tr>
                    <td class="col-sm-8 col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="${pageContext.request.contextPath}/theme${book.key.cover}" style="width: 72px; height: 72px;"> </a>
                        </div>
                    </td>
                    <td class="col-sm-1 col-md-1" style="text-align: center">
                        <div><a href="${pageContext.request.contextPath}/shoppingCart/addBook/${book.key.id}" style="text-decoration: none">+</a></div>
                        <input type="email" class="form-control"  value="${book.value}">
                        <div><a href="${pageContext.request.contextPath}/shoppingCart/removeBook/${book.key.id}" style="text-decoration: none">-</a></div>
                    </td>
                    <td class="col-sm-1 col-md-1 text-center"><strong>${book.key.price}</strong></td>
                    <td class="col-sm-1 col-md-1 text-center"><strong>${book.key.price * book.value}</strong></td>
                    <td class="col-sm-1 col-md-1">
                        <button type="button" class="btn btn-danger">
                            <span class="glyphicon glyphicon-remove"></span> Remove
                        </button></td>
                </tr>
                </c:forEach>

                <tr>
                    <td>   </td>
                    <td>   </td>
                    <td>   </td>
                    <td><h3>Total</h3></td>
                    <td class="text-right"><h3><strong>${total}</strong></h3></td>
                </tr>
                <tr>
                    <td>   </td>
                    <td>   </td>
                    <td>   </td>
                    <td>
                        <button type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-shopping-cart"></span><a href="${pageContext.request.contextPath}/books" style="text-decoration: none">Continue Shopping</a>
                        </button></td>
                    <td>
                        <button type="button" class="btn btn-success">
                            <a href="${pageContext.request.contextPath}/checkInCart">
                            Checkout <span class="glyphicon glyphicon-play"></span></a>
                        </button></td>
                </tr>
                </tbody>

            </table>
        </div>
    </div>
</div>

