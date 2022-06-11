<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<div class="meeting-wrapper">
    <c:if test="${errors != null}">
        <div id="flush_error">
            入力内容にエラーがあります。<br />
            <c:forEach var="error" items="${errors}">
                ・<c:out value="${error}" /><br />
            </c:forEach>

        </div>
    </c:if>

    <fmt:parseDate value="${meeting.date}" pattern="yyyy-MM-dd" var="dueDay" type="date" />
    <label for="${AttributeConst.MET_DATE.getValue()}">日付</label><br />
    <input type="date" name="${AttributeConst.MET_DATE.getValue()}" value="<fmt:formatDate value='${dueDay}' pattern='yyyy-MM-dd' />" />
    <br /><br />

    <label for="${AttributeConst.MET_NAME.getValue()}">会議名</label><br />
    <input type="text" name="${AttributeConst.MET_NAME.getValue()}" value="${meeting.name}" />
    <br /><br />

    <label for="${AttributeConst.MET_TEAM.getValue()}">チーム名</label><br />
    <select name="${AttributeConst.MET_TEAM.getValue()}">
      <option selected>チームを選んでください</option>
      <c:forEach var="team" items="${teams}">
        <option value="${team.id}" <c:if test="${team.id == meeting.team.id}">selected</c:if>>${team.name}</option>
      </c:forEach>
    </select>

    <input type="hidden" name="${AttributeConst.MET_ID.getValue()}" value="${meeting.id}" />
    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
    <br /><br />

    <button type="submit" class="submit-btn">作成</button>
    <br/>
</div>