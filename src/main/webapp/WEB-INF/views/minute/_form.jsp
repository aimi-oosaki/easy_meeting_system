<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />


<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<%-- <c:choose> --%>
<%--     <c:when test="${employee.name} != null"> --%>
<%--         <label for="${AttributeConst.EMPLOYEE.getValue()}">作成者： ${employee.name}</label><br /> --%>
<%--     </c:when> --%>
<%--     <c:otherwise> --%>
<%--         <label for="${AttributeConst.EMPLOYEE.getValue()}">作成者： ${minute.employee.name}</label><br /> --%>
<%--     </c:otherwise> --%>
<%-- </c:choose> --%>
<br />

<label for="${AttributeConst.MIN_ATTENDEES.getValue()}">参加者</label><br />
<textarea name="${AttributeConst.MIN_ATTENDEES.getValue()}" rows="2" cols="50">${minute.attendees}</textarea>
<br /><br />

<%--     <c:forEach var="i" begin="0" end="${list_size}" step="1"> --%>
 <c:forEach var="agenda" items="${agendas}" varStatus="loop">
     <label for="${AttributeConst.AGENDAS.getValue()}">${agenda.title}</label><br />
     <input type="hidden" name="${AttributeConst.AGE_ID.getValue()}[${loop.index}]" value="${agenda.id}" />
     <textarea name="${AttributeConst.AGE_SUMMARY.getValue()}[${loop.index}]" rows="5" cols="50">${agenda.summary}</textarea>
     <br /><br />
 </c:forEach>
<%-- </c:forEach> --%>


<label for="${AttributeConst.MIN_DECIDED.getValue()}">決定事項</label><br />
<textarea name="${AttributeConst.MIN_DECIDED.getValue()}" rows="10" cols="50">${minute.decided}</textarea>
<br /><br />

<label for="${AttributeConst.MIN_PENDING.getValue()}">保留事項</label><br />
<textarea name="${AttributeConst.MIN_PENDING.getValue()}" rows="10" cols="50">${minute.pending}</textarea>
<br /><br />

<input type="hidden" name="${AttributeConst.MET_ID.getValue()}" value="${meeting.id}" />
<input type="hidden" name="${AttributeConst.MIN_ID.getValue()}" value="${minute.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>