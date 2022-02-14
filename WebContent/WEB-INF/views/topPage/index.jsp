<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <c:choose>
            <c:when test="${sessionScope.login_user != null}">
               <h3>投稿やお気に入りをした店舗の営業状態が確認できます。</h3>
            </c:when>
            <c:otherwise>
                <h3>ログインしていないので登録されている全店舗の営業状態が表示されます。<br />
                ログインすると投稿やお気に入りをした店舗の営業状態が確認できます。</h3>
            </c:otherwise>
        </c:choose>
        <br />
        <fmt:formatNumber var="ct" value="${current_time}" pattern="#0,00" />
        <h3>${today_date} (${today_week}曜日) ${fn:replace(ct, ',', ':')}</h3>
        <h3>登録店 ${restaurants_count} 件中 ${open_restaurants_count} 件営業中です<br /></h3>
        <h3>【登録店舗　営業時間一覧】</h3>
        <table id="restaurant_list">
            <tbody>
                <tr>
                    <th class="restaurant_name">店名</th>
                    <th class="restaurant_time">開店時間</th>
                    <th class="restaurant_time">閉店時間</th>
                    <th class="restaurant_day">休業日</th>
                    <th class="restaurant_time">営業</th>
                </tr>
                <c:forEach var="usersrestaurant" items="${usersrestaurants}" varStatus="status">
                    <fmt:formatNumber var="open" value="${usersrestaurant.open_time}" pattern="#0,00" />
                    <fmt:formatNumber var="close" value="${usersrestaurant.close_time}" pattern="#0,00" />
                    <tr class="row${status.count % 2}">
                        <td class="restaurant_name"><a href="<c:url value='/restaurants/show?id=${usersrestaurant.id}' />"><c:out value="${usersrestaurant.name}" /></a></td>
                        <td class="restaurant_time"><c:out value="${fn:replace(open, ',', ':')}" /></td>
                        <td class="restaurant_time"><c:out value="${fn:replace(close, ',', ':')}" /></td>
                        <td class="restaurant_day"><c:out value="${usersrestaurant.closed_day}" /></td>
                        <td class="restaurant_time">
                            <c:choose>
                                <c:when test="${usersrestaurant.open == 1}">
                                    <div id="open">営業中</div>
                                </c:when>
                                <c:otherwise>
                                    <div id="close">閉店中</div>
                                </c:otherwise>
                            </c:choose>
                        </td>
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
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <c:if test="${sessionScope.login_user != null}">
           <p><a href="<c:url value='/restaurants/new' />">新規店舗の登録</a></p>
        </c:if>
    </c:param>
</c:import>