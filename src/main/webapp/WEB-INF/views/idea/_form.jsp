<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="actIde" value="${ForwardConst.ACT_IDE.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<!-- エラー内容 -->
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="${AttributeConst.IDE_CONTENT.getValue()}"></label><br />
<textarea name="${AttributeConst.IDE_CONTENT.getValue()}" rows="10" cols="50">${idea.content}</textarea>
<br /><br />

<input type="hidden" name="${AttributeConst.WAN_ID.getValue()}" value="${want.id}" />
<input type="hidden" name="${AttributeConst.IDE_ID.getValue()}" value="${idea.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit" class="submit-btn">投稿</button>