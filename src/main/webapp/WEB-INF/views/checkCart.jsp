<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>



<div class="container">
        <h1>Information Order</h1>
    <form method="post" action="/mybooks/order">

            <c:if test="${not editAddress}">
        <table>
            <tr>
                <td>UserName:</td>
                <td><input name="username" value="${auth.getUserInfo().getUser().displayName}" readonly="true" style="border: none"/></td>
            </tr>
            <tr>
                <td>Address ship:</td>
                <td><input type="text" name="address" value="${auth.getUserInfo().getUser().address}" readonly="true" style="border: none"/></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><input type="text" name="phone" value="${auth.getUserInfo().getUser().phone}" readonly="true" style="border: none"/></td>

            </tr>
        </table>
            </c:if>

    <c:if test="${editAddress}">
        <br/>
        <table>
            <tr>
                <td>UserName:</td>
                <td><input name="username" value="${addressInfo.username}" readonly="true" style="border: none"/></td>
            </tr>
            <tr>
                <td>Address ship:</td>
                <td><input name="address" type="text" value="${addressInfo.address}" readonly="true" style="border: none"/></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><input name="phone" type="text" value="${addressInfo.phone}" readonly="true" style="border: none"/></td>

            </tr>
        </table>
    </c:if>

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
                <c:forEach items="${cart}" var="cat" varStatus="tagStatus">
                    <tr>
                        <td class="col-sm-8 col-md-6">
                            <div class="media">
                                <input type="hidden" name="bookID" value="${cat.key.id}"/>
                                <a class="thumbnail pull-left" href="#"> <img class="media-object" src="${pageContext.request.contextPath}/theme${cat.key.cover}" style="width: 72px; height: 72px;"> </a>
                            </div>
                        </td>
                        <td class="col-sm-1 col-md-1" style="text-align: center">
                            <input name="quantity" value="${cat.value}" readonly="true" style="border: none"/>
                        </td>
                        <td class="col-sm-1 col-md-1 text-center"><strong><input name="price" value="${cat.key.price}" readonly="true" style="border: none"/> </strong></td>
                        <td class="col-sm-1 col-md-1 text-center"><strong><input name="totalPrice" value="${cat.key.price * cat.value}" readonly="true" style="border: none"/> </strong></td>
                    </tr>
                    </c:forEach>

                    <tr>
                        <td>   </td>
                        <td>   </td>
                        <td>   </td>
                        <td><h3>Total</h3></td>
                        <td class="text-right"><h3><strong><input name="totalPriceOrder" value="${total}" readonly="true" style="border: none"/></strong></h3></td>
                    </tr>

                    <tr>
                        <td>   </td>
                        <td>   </td>
                        <td>
                            <button type="button" class="btn btn-default">
                                <span class="glyphicon glyphicon-shopping-cart"></span><a href="${pageContext.request.contextPath}/editAddress" style="text-decoration: none">Edit address</a>
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-default">
                                <span class="glyphicon glyphicon-shopping-cart"></span><a href="${pageContext.request.contextPath}/books" style="text-decoration: none">Continue Shopping</a>
                            </button>
                        </td>
                        <td>
                            <button type="submmit" class="btn btn-danger">

                                Order <span class="glyphicon glyphicon-play"></span>
                            </button>
                        </td>
                    </tr>
                    </tbody>

            </table>
        </div>
    </div>
        </form>
</div>


