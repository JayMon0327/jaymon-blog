<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>
          <form>
            <input type="hidden" id="id" value="${board.id}"/>
            <div class="form-group">
              <input value="${board.title}" type="text" class="form-control" id="title">
            </div>

            <div class="form-group">
              <textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
            </div>
          </form>

            <button class="btn btn-primary w-100 py-2" id= "btn-update">글쓰기 완료</button>

<script>
$('.summernote').summernote({
  placeholder: 'Summernote for Bootstrap 5',
  tabsize: 2,
  height: 400,
  lang: 'ko-KR'
});
</script>

<script src="/js/board.js"></script>
<%@ include file = "../layout/footer.jsp" %>

