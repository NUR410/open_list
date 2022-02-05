<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ようこそ</h2>
        <h3>今日は ${today_date} (${today_week}曜日)</h3>
        <h3>現在の時刻は ${current_time} です</h3>
        <h3>【お気に入り店舗　一覧】</h3>
        <table id="restaurant_list">
            <tbody>
                <tr>
                    <th class="restaurant_name">店名</th>
                    <th class="restaurant_time">開店時間</th>
                    <th class="restaurant_time">閉店時間</th>
                    <th class="restaurant_day">休業日</th>
                </tr>
                <c:forEach var="usersrestaurant" items="${usersrestaurants}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="restaurant_name"><a href="<c:url value='/restaurants/show?id=${usersrestaurant.restaurant.id}' />"><c:out value="${usersrestaurant.restaurant.name}" /></a></td>
                        <td class="restaurant_time"><c:out value="${usersrestaurant.restaurant.open_time}" /></td>
                        <td class="restaurant_time"><c:out value="${usersrestaurant.restaurant.close_time}" /></td>
                        <td class="restaurant_day"><c:out value="${usersrestaurant.restaurant.closed_day}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${restaurants_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((restaurants_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/restaurants/new' />">新規店舗の登録</a></p>
    </c:param>
</c:import>