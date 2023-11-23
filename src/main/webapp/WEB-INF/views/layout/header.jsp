<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="/css/bootstrap.min.css" integrity="sha384-
      ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
              crossorigin="anonymous">
        <!-- Custom styles for this template -->
        <link href="/css/jumbotron-narrow.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <title>Title</title>
</head>
<body>
<!-- BootStrap Navigation Bar Sample -->
<header class="p-3 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
          <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
        </a>
        <c:choose>
            <c:when test= "${empty sessionScope.principal}">
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                          <li><a href="/" class="nav-link px-2 text-secondary">Home</a></li>
                          <li><a href="/loginForm" class="nav-link px-2 text-white">로그인</a></li>
                          <li><a href="/joinForm" class="nav-link px-2 text-white">회원가입</a></li>
                          <li><a href="#" class="nav-link px-2 text-white">FAQs</a></li>
                          <li><a href="#" class="nav-link px-2 text-white">About</a></li>
                        </ul>
            </c:when>
            <c:otherwise>
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                          <li><a href="/" class="nav-link px-2 text-secondary">Home</a></li>
                          <li><a href="/board/Form" class="nav-link px-2 text-white">글쓰기</a></li>
                          <li><a href="/user/form" class="nav-link px-2 text-white">회원정보</a></li>
                          <li><a href="/logout" class="nav-link px-2 text-white">로그아웃</a></li>
                          <li><a href="#" class="nav-link px-2 text-white">About</a></li>
                        </ul>
            </c:otherwise>
        </c:choose>

        <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
          <input type="search" class="form-control form-control-dark" placeholder="Search..." aria-label="Search">
        </form>

        <div class="text-end">
        </div>
      </div>
    </div>