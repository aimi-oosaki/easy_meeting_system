<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="action" value="${ForwardConst.ACT_TAS.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <div class="task-edit">
        <h2>プロジェクト 新規登録</h2>

        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
        </form>
        <br /><br />
        <p><a href="<c:url value='?action=${action}&command=${commIdx}' />">一覧に戻る</a></p>
        </div>
        </div>
    </c:param>
</c:import>