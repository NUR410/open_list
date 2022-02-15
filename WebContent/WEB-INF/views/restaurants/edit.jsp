<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${restaurant != null}">
                <h3>店舗　編集ページ</h3>
                <form method="POST" action="<c:url value='/restaurants/update' />">
                    <c:import url="_form.jsp" />
                </form>
                <p><a href="#" onclick="confirmDestroy();">この店舗情報を削除する</a></p>
                <form method="POST" action="<c:url value='/restaurants/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                    <input type="hidden" name="restaurant_id" value="${restaurant.id}" />
                </form>
                <script>
                    function confirmDestroy() {
                        if(confirm("本当に削除してよろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/restaurants/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>