<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actCom" value="${ForwardConst.ACT_COM.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />


<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="${AttributeConst.COM_NAME.getValue()}">会社名</label><br />
<input type="text" name="${AttributeConst.COM_NAME.getValue()}" value="${company.name}" />
<br /><br />

<input type="hidden" name="${AttributeConst.COM_ID.getValue()}" value="${company.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>