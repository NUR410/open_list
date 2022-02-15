<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h3>全店舗　一覧</h3>
        <table id="restaurant_list">
            <tbody>
                <tr>
                    <th class="restaurant_name">店名</th>
                    <th class="restaurant_name">投稿者</th>
                    <th class="restaurant_action">操作</th>
                </tr>
                <c:forEach var="restaurant" items="${restaurants}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="restaurant_name"><c:out value="${restaurant.name}" /></td>
                        <td class="restaurant_name">
                            <c:choose>
                                <c:when test="${restaurant.user.delete_flag == 0}">
                                    <a href="<c:url value='/users/show?id=${restaurant.user.id}' />"><c:out value="${restaurant.user.name}" /></a>
                                </c:when>
                                <c:otherwise>
                                    (削除済)
                                </c:otherwise>
                            </c:choose>
                        </td>
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
        <c:if test="${sessionScope.login_user != null}">
           <p><a href="<c:url value='/restaurants/new' />">新規店舗の登録</a></p>
        </c:if>
    </c:param>
</c:import>