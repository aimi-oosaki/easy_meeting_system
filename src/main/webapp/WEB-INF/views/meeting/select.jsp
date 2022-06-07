<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="commProb" value="${ForwardConst.CMD_PROB1.getValue()}" />
<c:set var="commIndex" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
    <div class="content-wrapper">
        <%-- 画像メニュー --%>
        <div id="home_menus_wrapper">
            <div class="top_menus">
                <div class="top-menu">
                    <a href="<c:url value='?action=Meeting&command=${commProb}&meeting_id=${meeting.id}' />"><img class="home_img" src="/easy_meeting_system/img/newMeeting.png"></a>
                    <a href="<c:url value='?action=Meeting&command=${commProb}&meeting_id=${meeting.id}' />" class="link-secondary text-decoration-none">課題解決会議</a>
                </div>
                <div class="top-menu">
                   <a href="<c:url value='?action=Task&command=${commIndex}' />" class="top_menu"><img class="home_img" src="/easy_meeting_system/img/meetingIndex.png"></a>
                    <a href="<c:url value='?action=Task&command=${commIndex}' />" class="link-secondary text-decoration-none">進捗会議</a>
                </div>
                <div class="top-menu">
                    <a href="<c:url value='?action=Todo&command=${commIndex}' />" class="top_menu"><img class="home_img" src="/easy_meeting_system/img/todo.png"></a>
                    <a href="<c:url value='?action=Todo&command=${commIndex}' />" class="link-secondary text-decoration-none">アイデア会議</a>
                </div>
            </div>
        </div>
    </div>
    </c:param>
</c:import>