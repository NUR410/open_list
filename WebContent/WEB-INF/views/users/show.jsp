<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${user != null}">
                <h3>${user.name} さんのユーザー情報　詳細ページ</h3>

                <table>
                    <tbody>
                        <tr>
                            <th>id</th>
                            <td><c:out value="${user.id}" /></td>
                        </tr>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${user.name}" /></td>
                        </tr>
                    </tbody>
                </table>
                <c:if test="${sessionScope.login_user.id == user.id}">
                    <p><a href="<c:url value='/users/edit?id=${user.id}' />">ユーザー情報を編集する</a></p>
                </c:if>

                <h3>${user.name}さんの投稿した店舗一覧</h3>
        <table id="restaurant_list">
            <tbody>
                <tr>
                    <th class="restaurant_name">店名</th>
                    <th class="restaurant_action">操作</th>
                </tr>
                <c:forEach var="restaurant" items="${restaurants}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="restaurant_name"><c:out value="${restaurant.name}" /></td>
                        <td class="restaurant_action"><a href="<c:url value='/restaurants/show?id=${restaurant.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全${restaurants_count}件中 ${((page - 1) * 15) + 1}〜${((page - 1) * 15) + size} を表示中 ）<br />
            <c:forEach var="i" begin="1" end="${((restaurants_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/restaurants/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/users/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>