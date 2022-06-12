<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTea" value="${ForwardConst.ACT_TEA.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />


<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<br />
<label for="${AttributeConst.TEA_NAME.getValue()}">チーム名</label><br />
<input id="team-p" type="text" name="${AttributeConst.TEA_NAME.getValue()}" value="${team.name}" />
<br /><br />

<input type="hidden" name="${AttributeConst.TEA_ID.getValue()}" value="${team.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit" class="submit-btn">投稿</button>