<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>

    <main class="form-signin w-100 m-auto">
      <form>
        <input type="hidden" id="id" value="${principal.user.id}"/>
        <h1 class="h3 mb-3 fw-normal">회원 정보 수정</h1>

        <div class="form-floating">
          <label for="username">Username</label>
          <input type="username" value="${principal.user.username}" class="form-control"
          id="username" placeholder="username" readonly>
        </div>

        <div class="form-floating">
          <label for="password">password</label>
          <input type="password" class="form-control"
          id="password" placeholder="password">
        </div>

        <div class="form-floating">
          <label for="email">Email</label>
          <input type="email" value="${principal.user.email}" class="form-control" id="email" placeholder="Enter email">
        </div>

        <p class="mt-5 mb-3 text-body-secondary">© 2017–2023</p>
      </form>
        <button class="btn btn-primary w-100 py-2" id="btn-update">수정완료</button>

    </main>

<script src="/js/user.js"></script>
<%@ include file = "../layout/footer.jsp" %>

