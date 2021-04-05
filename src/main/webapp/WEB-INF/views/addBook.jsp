<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<div class="col-md-10">
    <c:choose>
        <c:when test="${edit}">
            <h3 class="my-4">Edit book</h3>
        </c:when>
        <c:otherwise>
            <h3 class="my-4">Add new book</h3>
        </c:otherwise>
    </c:choose>

    <c:if test="${added}">
        Add Success. View <a href="${pageContext.request.contextPath}/book/${bookID}">${bookTitle}</a> OR <a href="${pageContext.request.contextPath}/books/add">Add new book</a>
    </c:if>
    <c:if test="${edited}">
        Edit Success. View <a href="/mybooks/book/${bookId}">${bookTitle}</a>
    </c:if>
    <c:if test="${not added and not edited}">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">
                    ${errorMsg}<br/>
            </div>
        </c:if>
        
        <form:form modelAttribute="book" enctype="multipart/form-data" >
            <form:errors path="*" cssClass="alert alert-danger" element="div" />
            <div class="form-group">
                <label for="title">Title</label>
                <form:input type="text" class="form-control" path="title" id="title" value="${book.title}" placeholder="Title"/>
                <form:errors path="title" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="page">Page</label>
                <form:input type="text" class="form-control" path="pages" id="title" value="${book.pages}" placeholder="Page"/>
                <form:errors path="pages" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="title">PublishYear</label>
                <form:input type="text" class="form-control" path="publishYear" id="title" value="${book.title}" placeholder="PublishYear"/>
                <form:errors path="publishYear" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="title">OriginalTitle</label>
                <form:input type="text" class="form-control" path="originalTitle" id="title" value="${book.title}" placeholder="OriginalTitle"/>
                <form:errors path="originalTitle" cssClass="text-danger" />
            </div>

            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">$</span>
                </div>
                <input type="text" value="${book.price}" placeholder="price" name="price"/>
                <div class="input-group-append">
                    <span class="input-group-text">.00</span>
                </div>
            </div>

            <%--TODO ADD AUTHOR --%>
            <form:input path="authors" id="authors" value="${book.authors}" autocomplete="false" hidden="hidden"/>
            <div class="form-group">
                <label for="authorSearch">Author</label>
                <select id="authorSearch" class="form-control">
                    <option value="0">Select</option>
                    <c:forEach items="${authors}" var="author" varStatus="tagStatus">
                        <option value="${author.id}">${author.firstName} ${author.lastName}</option>
                    </c:forEach>
                </select>
                <div id="authorList" class="list_option"></div>


                <button type="button" id="newAuthorBtn" class="btn btn-link">New Author</button>
                <div id="newAuthorForm" class="input-group">
                    <input type="text" id="add_new_author_name" class="form-control" placeholder="name"/>
                    <input type="text" id="add_author_lastname" class="form-control" placeholder="lastname"/>
                    <div class="input-group-append">
                        <button id="add_new_author"class="btn btn-outline-secondary" type="button">Add new author</button>
                    </div>
                </div>
            </div>

            <%--TODO ADD CATEGORY --%>
            <div class="form-group">
                <label for="cats">Category</label>
                <form:select path="category" value="${book.category.id}" id="cats" cssClass="form-control">
                    <form:options itemValue="0" label="Select"/>
                    <c:forEach items="${cats}" var="cat">
                        <c:choose>
                            <c:when test="${cat.id == book.category.id}">
                                <option value="${cat.id}" selected>${cat.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${cat.id}">${cat.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
                <form:errors path="category" cssClass="text-danger"/>
            </div>

            <div class="form-group">
                <label for="title">Cover</label>
                <form:input type="file" class="form-control" path="upload" id="title" value="${book.cover}" placeholder="Cover"/>
                <form:errors path="upload" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="title">Description</label>
                <form:input type="text" class="form-control" path="description" id="title" value="${book.description}" placeholder="Description"/>
                <form:errors path="description" cssClass="text-danger" />
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
        </form:form>
    </c:if>
</div>
<script type="text/javascript">
    $(function (){
        $('#authorSearch').on("change", function (){
            var authorSelected = $(this).find(":selected");
            var id = authorSelected.val();
            var name = authorSelected.text();

            if(id != 0){
                addAuthor(id, name);

                $(this).removeAttr("selected");
                $("#authorSearch")[0].selectedIndex = 0;
            }
        });

        function addAuthor(id, name){
            var authors = $('#authors').val();

            var authorArray = authors.split(",");
            if(!authorArray.includes(id)){
                authors += id + ",";
                $('#authors').val(authors);
                $('#authors').attr('value', authors);

                authorListAppend(id, name);
            }
        }

        function authorListAppend(id, name){
            output = "" +
                "<div id=\"author"+id+"\" class=\"author\">\n" +
                "<div class=\"name\">"+name+"</div>\n" +
                "<div class=\"action\"><button type=\"button\" authorid=\""+id+"\" class=\"btn btn-link\">Remove</button></div>\n" +
                "</div>";
            $("#authorList").append(output);
        }
        $('#authorList').on("click", "button", function () {
            var id = $(this).attr("authorid");
            $( ".hello" ).remove();
            $("#author"+id).remove();
            var authors = $('#authors').val();
            authors = authors.replace(id+"," , "");
            $('#authors').val(authors);
            $('#authors').attr('value', authors);
            //console.log(id);
        });
        $("#newAuthorBtn").click(function () {
            console.log("hello");
            var display =  $("#newAuthorForm").css("display");
            if(display == "none") {
                $("#newAuthorBtn").text("Author");
                $("#newAuthorForm").css("display", "flex");
            }
            else {
                $("#newAuthorBtn").text("New Author");
                $("#newAuthorForm").css("display", "none");
            }
        });

        $("#add_new_author").click(function (){
            var name = $('#add_new_author_name').val();
            var lastname = $('#add_author_lastname').val();
            if(name != ''){
                var author = {
                    firstName: name,
                    lastName: lastname
                }
            }

            $.ajax({
                url: 'http://localhost:8080/mybooks/api/author/add',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(author)
            }).done(addAuthorRes)
            .fail(function (e){
                console.log("ERROR: ",e);
            });
        });

        function addAuthorRes(res){
            if(res.status === 'done'){
                addAuthor(res.data.id,res.data.firstName + " " +res.data.lastName);

                $("#authorSearch").append(new Option(res.data.firstName + " " + res.data.lastName, res.data.id));

                $("#newAuthorForm").css("display","none");
                $("#newAuthorBtn").text("new author");
                $('#add_new_author_name').val("");
                $('#add_new_author_name').attr('value', "");
                $('#add_new_author_lastname').val("");
                $('#add_new_author_lastname').attr('value', "");
            }
        }

        function showAuthors(){
            var authors = $('#authors').val();
            var authorArray = authors.split(",");
            authorArray.forEach(function (id) {
                id = parseInt(id);
                if(!isNaN(id) && id !== 0) {
                    var name = $('#authorSearch option[value="'+id+'"]').text();
                    authorListAppend(id, name);
                }
            });
        }
        showAuthors();
    })
</script>