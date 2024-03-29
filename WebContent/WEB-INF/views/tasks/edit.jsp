<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${task != null}">
                <h2>${task.id} 番目のタスクを編集</h2>

                <form method="POST" action="${pageContext.request.contextPath}/update">
                    <c:import url="_form.jsp"/>
                </form>

                <p><a href="#" onclick="confirmDestroy();">【タスク完了！】</a></p>
                <br />
                <p><a href="${pageContext.request.contextPath}/index">一覧に戻る</a></p>
                <form method="POST" action="${pageContext.request.contextPath}/destroy">
                    <input type="hidden" name="_token" value="${_token}" />
                </form>
                <script>
                function confirmDestroy(){
                    if(confirm("おつかれさまです！ タスクを削除してよろしいですか？")){
                           document.forms[1].submit();
                    }
                }
                </script>
            </c:when>
            <c:otherwise>
                <h2>タスクが見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>