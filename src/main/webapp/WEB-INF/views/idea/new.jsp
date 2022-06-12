<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_IDE.getValue()}" />
<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <h2>アイデア　新規登録</h2>
        <!-- フォーム -->
        <h2>募集</h2>
        <p>タイトル</p>
        <c:out value="${want.title}" />
        <br /><br />

        <p>内容</p>
        <c:out value="${want.content}" />
        <br /><br />

        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='?action=${actWan}&command=${commIdx}' />">一覧に戻る</a></p>
        </div>
    </c:param>
</c:import>