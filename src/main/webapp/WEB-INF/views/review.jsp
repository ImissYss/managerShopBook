<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-12">
    <h3 class="my-4">Review : ${review.title}</h3>
    <img class="book-cover" src="${pageContext.request.contextPath}/theme${review.book.cover}"/><br/>
    Title: ${review.title} <br/>
    Date: ${review.getDate()} <br/>
    View count: ${review.viewCount} <br/>
    Author: <a href="/mybooks/user/${review.user.id}">${review.user.getDisplayName()}</a><br/>
    Content: ${review.content} <br/><br/>

    Book: <a href="/mybooks/book/${review.book.id}">${review.book.title}</a><br/>

    <c:if test="${auth.getUserInfo().isAdminOrModerator()}">
        <a href="/mybooks/reviews/edit/${review.id}">Edit review</a>
    </c:if>
    <br/>
    <br/>

    Comment (<span id="commentCount"></span>):
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addCommentModal">
        Add comment
    </button>

    <br/>
    <div class="modal fade" id="addCommentModal" tabindex="-1" role="dialog" aria-labelledby="addCommentModal" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add comment</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label  class="col-form-label">Content:</label>
                            <textarea  class="form-control" id="contentComment" rows="4"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" id="addCommentBtn" class="btn btn-primary">Add</button>
                </div>
            </div>
        </div>
    </div>
    <div id="comments"></div>
    <button type="button" id="moreComments" class="btn btn-primary">More comment</button>


    <script type="text/javascript">
        console.log(${review.id});
        $(function () {
            $("#addCommentBtn").click(function () {
                var content = $('#contentComment').val();
                console.log(content);
                if(content != ''){
                    var comment = {
                        content: content,
                        reviewID: ${review.id}
                    };
                    //addCommentRes(comment);
                    $.ajax({
                        url: 'http://localhost:8080/mybooks/api/comment/add',
                        type: 'post',
                        dataType: 'json',
                        contentType: 'application/json',
                        data: JSON.stringify(comment)
                    }).done(addCommentRes)
                    .fail(function (e){
                        console.log("Error: ",e);
                    });

                }
            });

            function addCommentRes(res){
                if (res.status === 'done'){
                    $('#addCommentModal').modal('hide');
                    $('#contentComment').val("");
                    location.reload();
                }
            }

            var commentsPage = 1;
            var commentsDisabled = false;
            getComments();


            function getComments(){
                if(commentsDisabled)
                    return;
                $.ajax({
                   url: 'http://localhost:8080/mybooks/api/comment/get/${review.id}?page=' + commentsPage,
                   type: 'get'
                }).done(getCommentsRes)
                .fail(function (e){
                    console.log("ERROR: ",e);
                });
            }

            function getCommentsRes(res){
                if(res.status === 'done'){
                    commentsPage++;
                    var output = "";
                    $.each(res.data.content, function (index, c){
                        output+= "<div class='comment'>" +
                            " <span class='author'> Author: <a href='/mybooks/user/"+ c.userID +"'>" + c.userName +
                            "</a></span><span class='data'> Data: " + c.addDate + "</span>" +
                            "<p>" + c.content + "</p>" +
                            "</div>";
                    });

                    $('#comments').prepend(output);
                    $('#commentCount').text(res.data.totalCount);

                    if(res.data.page === res.data.totalPage){
                        disableMoreComments();
                    }
                    else{
                        disableMoreComments();
                        $('#commentCount').text("0");
                    }
                }

                function disableMoreComments(){
                    commentsDisabled = true;
                    $('#moreComments').css("display","none");
                    $('#moreComments').addClass("disabled");
                }

                $('#moreComments').click(function (){
                    getComments();
                });
            }


            });
    </script>
</div>