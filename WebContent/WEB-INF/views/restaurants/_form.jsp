<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="name">店舗名</label><br />
<input type="text" name="name" value="${restaurant.name}" />
<br /><br />

<label for="open_time">開店時間</label><br />
<fmt:formatNumber var="open" value="${restaurant.open_time}" pattern="00,00" />
<input type="time" name="open_time" value="${fn:replace(open, ',', ':')}" />
<br /><br />

<label for="close_time">閉店時間</label><br />
<fmt:formatNumber var="close" value="${restaurant.close_time}" pattern="00,00" />
<input type="time" name="close_time" value="${fn:replace(close, ',', ':')}" />
<br /><br />

<label for="closed_day">休業日</label><br />
<input type="checkbox" name="closed_day" value="月" <c:if test="${cd.get(0)}">checked</c:if>>月曜日
<input type="checkbox" name="closed_day" value="火" <c:if test="${cd.get(1)}">checked</c:if>>火曜日
<input type="checkbox" name="closed_day" value="水" <c:if test="${cd.get(2)}">checked</c:if>>水曜日
<input type="checkbox" name="closed_day" value="木" <c:if test="${cd.get(3)}">checked</c:if>>木曜日
<input type="checkbox" name="closed_day" value="金" <c:if test="${cd.get(4)}">checked</c:if>>金曜日
<input type="checkbox" name="closed_day" value="土" <c:if test="${cd.get(5)}">checked</c:if>>土曜日
<input type="checkbox" name="closed_day" value="日" <c:if test="${cd.get(6)}">checked</c:if>>日曜日
<input type="checkbox" name="closed_day" value="祝" <c:if test="${cd.get(7)}">checked</c:if>>祝日
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>