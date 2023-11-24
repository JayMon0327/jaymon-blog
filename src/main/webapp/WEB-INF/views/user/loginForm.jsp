<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>

    <main class="form-signin w-100 m-auto">
      <form action="/auth/loginProc" method="post">
        <h1 class="h3 mb-3 fw-normal">로그인</h1>

        <div class="form-floating">
          <input type="username" name="username" class="form-control" id="username" placeholder="username">
          <label for="username">Username</label>
        </div>

        <div class="form-floating">
          <input type="password" name="password" class="form-control" id="password" placeholder="password">
          <label for="password">password</label>
        </div>

        <button class="btn btn-primary w-100 py-2" id= "btn-login">로그인</button>
      </form>
    </main>

<%@ include file = "../layout/footer.jsp" %>

