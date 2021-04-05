<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${title}</title>

    <!-- Bootstrap core CSS -->
    <link href="/mybooks/theme/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet" >

    <!-- Custom styles for this template -->
    <link href="/mybooks/theme/css/style.css" rel="stylesheet">
    <!-- Bootstrap core JavaScript -->
    <script src="/mybooks/theme/lib/jquery/jquery.min.js"></script>
    <script src="/mybooks/theme/lib/bootstrap/js/bootstrap.min.js"></script>
    <script src="/mybooks/theme/lib/lodash/lodash.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="/mybooks/theme/js/main.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <<button id="toggleSidebarBtn" class="navbar-toggler" type="button">
           <i class="fas fa-align-left"></i>
        </button>
        <a class="navbar-brand" href="<c:url value="/" />">My Books</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navabrResponsive">
            <ul class="navbar-nav ml-auto">
                <tiles:insertAttribute name="navigation"/>
            </ul>
        </div>
    </div>
</nav>
<!-- Page Content -->
<div class="container">
    <div class="row">
        <div id="sidebar" class="col-md-4">
            <tiles:insertAttribute name="sidebar"/>
        </div>

        <div class="col-md-8">
            <tiles:insertAttribute name="content"/>
        </div>
    </div>
</div>
<!-- footer -->
<tiles:insertAttribute name="footer"/>
</body>