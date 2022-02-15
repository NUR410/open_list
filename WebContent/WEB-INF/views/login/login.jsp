<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${hasError}">
            <div id="flush_error">
                名前かパスワードが間違っています。
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h3>ログイン</h3><br />
        <form method="POST" action="<c:url value='/login' />">
            <label for="name">ユーザー名</label><br />
            <input type="text" name="name" value="${name}" />
            <br /><br />

            <label for="password">パスワード</label><br />
            <input type="password" name="password" />
            <br /><br />

            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit">ログイン</button>
        </form>
                <br /><br /><br />
                <h3>ユーザー　新規登録</h3><br />
        <form method="POST" action="<c:url value='/users/create' />">
            <c:import url="../users/_form.jsp" />
        </form>
    </c:param>
</c:import>