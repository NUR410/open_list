<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<input type="time" name="open_time" value="${restaurant.open_time}" />
<br /><br />

<label for="close_time">閉店時間</label><br />
<input type="time" name="close_time" value="${restaurant.close_time}" />
<br /><br />

<label for="closed_day">休業日</label><br />
<input type="checkbox" name="closed_day" value="月">月曜日
<input type="checkbox" name="closed_day" value="火">火曜日
<input type="checkbox" name="closed_day" value="水">水曜日
<input type="checkbox" name="closed_day" value="木">木曜日
<input type="checkbox" name="closed_day" value="金">金曜日
<input type="checkbox" name="closed_day" value="土">土曜日
<input type="checkbox" name="closed_day" value="日">日曜日
<input type="checkbox" name="closed_day" value="祝">祝日
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>