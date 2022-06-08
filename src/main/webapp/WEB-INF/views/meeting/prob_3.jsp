<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commProb2" value="${ForwardConst.CMD_PROB2.getValue()}" />
<c:set var="commProb3" value="${ForwardConst.CMD_PROB2.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
    <div id="content-wrapper">
        <c:import url="_timer.jsp" /><br />
        <div class="question-contaniner">
            <p>どうすればそうなる？</p>
        </div>
        <br />
        <input class= "mee-btn-left" type="button" onclick="location.href='<c:url value='?action=${actMee}&command=${commProb2}&meeting_id=${meeting.id}' />'" value="戻る">
        <input class= "mee-btn-right" type="button" onclick="location.href='<c:url value='?action=${actTod}&command=${commNew}&meeting_id=${meeting.id}' />'" value="次へ">
    </div>
    </c:param>
</c:import>