<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />


<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<fmt:parseDate value="${want.due_date}" pattern="yyyy-MM-dd" var="dueDay" type="date" />
<label for="${AttributeConst.WAN_DUEDATE.getValue()}">締切</label><br />
<input class="want-edit" type="date" name="${AttributeConst.WAN_DUEDATE.getValue()}" value="<fmt:formatDate value='${dueDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="${AttributeConst.WAN_MEETING.getValue()}">会議名</label><br />
<select name="${AttributeConst.WAN_MEETING.getValue()}" class="want-edit">
  <option>会議を選んでください</option>
  <c:forEach var="meeting" items="${meetings}">
    <option value="${meeting.id}" <c:if test="${meeting.id == want.meeting.id}">selected</c:if>>${meeting.name}</option>
  </c:forEach>
</select>

<br /><br />
<label  for="${AttributeConst.WAN_TITLE.getValue()}">タイトル</label><br />
<input class="want-edit" type="text" name="${AttributeConst.WAN_TITLE.getValue()}" value="${want.title}" />
<br /><br />

<label for="${AttributeConst.WAN_CONTENT.getValue()}">内容</label><br />
<textarea name="${AttributeConst.WAN_CONTENT.getValue()}" rows="10" cols="50">${want.content}</textarea>
<br /><br />

<input type="hidden" name="${AttributeConst.WAN_ID.getValue()}" value="${want.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit" class="submit-btn">投稿</button>