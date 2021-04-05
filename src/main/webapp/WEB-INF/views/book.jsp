<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Book : ${book.title}</h3>
    <img class="book-cover" src="${pageContext.request.contextPath}/theme${book.cover}"><br/>
    Title : ${book.title} <br/>

    Author:
    <c:forEach items="${book.authors}" var="author" varStatus="tagStatus">
        <a href="/books/author/${author.id}">${author.getDisplayName()}</a>
    </c:forEach>
    <br/>

    Star: ${book.stars}/6 <br/>
    Rating count: ${book.ratingCount}

    <%--
    TODO Rating
    --%>

    <c:if test="${auth.isLoggedIn()}">
        <br/>
        Your Rating:
        <c:if test="${not empty rating}">
            <span id="userStar">${rating.stars}</span><br/>
        </c:if>

        <select id="ratingBook" class="form-control">
            <option value="0">Select</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
        </select>
        <input type="text" id="bookID" value="${book.id}" autocomplete="false" hidden="hidden"/>
        <br/>
    </c:if>



    <%--
    TODO Vote Book
    --%>
    <c:if test="${auth.isLoggedIn()}">
    <div class="vote-box">
        <div class="vote-type">
            <div id="voteCountRead" class="vote-count"><c:out value="${not empty vote_READ ? voteREAD : 0}"/></div>
            <button type="button" id="read" class="btn btn-outline-success">READ</button>
        </div>
        <div class="vote-type">
            <div id="voteCountReading" class="vote-count"><c:out value="${not empty vote_READING ? vote_READING : 0}"/></div>
            <button type="button" id="reading" class="btn btn-outline-success">READING</button>
        </div>
        <div class="vote-type">
            <div id="voteCountWantRead" class="vote-count"><c:out value="${not empty vote_WANT_READ ? vote_WANT_READ : 0}"/></div>
            <button type="button" id="wantRead" class="btn btn-outline-success">WANT-READ</button>
        </div>
    </div>
    </c:if>





    <%-- TODO Category--%>

    <br/><br/>
    Description: ${book.description}<br/><br/>

    Pages: ${book.pages}<br/>

    Price: ${book.price}$<br/>

    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        <br/>
        <a href="/mybooks/books/edit/${book.id}">Edit Book</a>
    </c:if>

    <c:if test="${auth.isLoggedIn()}">
        <br/>
        <a href="/mybooks/reviews/${book.id}/add">Add Review</a>
    </c:if>
    <c:if test="${ not auth.getUserInfo().isAdminOrModerator() and auth.isLoggedIn()}">
        <br/>
        <a href="/mybooks/shoppingCart/addBook/${book.id}">Add Cart</a>
    </c:if>
    <br/>
    <br/>

    <%--TODO Revies list--%>

</div>

<script type="text/javascript">
    $(function (){
        <c:if test="${auth.isLoggedIn()}">
        $('#ratingBook').on("change", function (){
            var ratingSelected = $(this).find(":selected");
            var stars = ratingSelected.val();
            var bookID = $('#bookID').val();
            if(stars != 0){
                var rating = {
                    stars: stars,
                    bookID: bookID
                }

                $.ajax({
                    url: 'http://localhost:8080/mybooks/api/rating/add',
                    type: 'post',
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify(rating)
                }).done(addRatingRes)
                .fail(function (e){
                    console.log("ERROR: ",e);
                });
            }
        })

        function addRatingRes(res){
            console.log(res);
            if(res.status === "done"){
                console.log("done");
                $("#userStar").text(res.data.stars);
            }
        }

        var currentVote = "";

        $('#read').on("click", function (){
            console.log("hello");
            prepareVoteBook("READ", $(this));
        });
        $('#reading').on("click", function (){
            prepareVoteBook("READING", $(this));
        })
        $('#wantRead').on("click", function () {
            prepareVoteBook("WANT_READ", $(this));
        });

        function prepareVoteBook(voteType, el){
            if(
                (
                    voteType == "READ" ||
                        voteType == "READING" ||
                        voteType == "WANT_READ"
                )
                && el.hasClass("active")
            ){
                console.log("remove");
                voteBook("REMOVE");
            }
            else{
                console.log("voteType");
                console.log(voteType);
                voteBook(voteType);
            }
        }
        function voteBook(voteType){
            var bookID =$("#bookID").val();
            console.log(bookID);
            var vote = {
                voteType: voteType,
                bookID: bookID
            }

            $.ajax({
                url: 'http://localhost:8080/mybooks/api/vote_book',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(vote)
            }).done(voteBookRes)
            .fail(function (e){
                console.log("ERROR: ",e);
            });
        }

        function voteBookRes(res){
            console.log(res);
            if(res.status === "done"){
                console.log("done");
                prepareIncVote(res.data.voteType);
                showVoteBook(res.data);
            }
        }

        function showVoteBook(data){
            $('#read').removeClass("active");
            $('#reading').removeClass("active");
            $('#wantRead').removeClass("active");

            if(data.voteType === "READ"){
                $('#read').addClass("active");
            }
            else if(data.voteType === "READING"){
                $('#reading').addClass("active");
            }
            else if(data.voteType === "WANT_READ"){
                $('#wantRead').addClass("active");
            }
            $(":focus").blur();
        }
        <c:if test="${not empty vote}">
        currentVote = "<c:out value="${vote.voteType}"/>";
        selectVoteBook(currentVote);
        </c:if>

        function selectVoteBook(voteType){
            showVoteBook({voteType: voteType});
        }
        function prepareIncVote(voteType) {
            if(voteType === "READ"){
                incVote($('#voteCountRead'));
            }
            else if(voteType === "READING"){
                incVote($('#voteCountReading'));
            }
            else if(voteType === "WANT_READ"){
                incVote($('#voteCountWantRead'));
            }
            decVote(voteType);
        }
        function incVote(el) {
            var count = parseInt(el.text());
            if(count >=0) {
                count = count + 1;
            }
            el.text(count);
        }
        function decVote(voteType) {
            if(currentVote !== "") {
                var el = $('#voteCountRead');
                if(currentVote === "READING"){
                    el = $('#voteCountReading');
                }
                else if(currentVote === "WANT_READ"){
                    el = $('#voteCountWantRead');
                }
                var count = parseInt(el.text());
                if(count > 0) {
                    count = count - 1;
                }
                el.text(count);
            }
            if(voteType !== "REMOVE") {
                currentVote = voteType;
            } else {
                currentVote = "";
            }
        }
        </c:if>
        <c:if test="${not auth.isLoggedIn()}">
        $('.vote-box button').on("click", function () {
            window.location.href = "/mybooks/login";
        });

        </c:if>
    })
</script>
