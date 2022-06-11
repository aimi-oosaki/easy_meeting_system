<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="actAge" value="${ForwardConst.ACT_AGE.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIndex" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commIndexMee" value="${ForwardConst.CMD_INDEX_MEE.getValue()}" />
<c:set var="commProb1" value="${ForwardConst.CMD_PROB1.getValue()}" />
<c:set var="commSel" value="${ForwardConst.CMD_SELECT.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
            <table id="meeting_list" class="table">
                <tbody>
                    <tr>
                        <th class="text-center">日程</th>
                        <th class="text-center">会議名</th>
                        <th class="text-center">議題</th>
                        <th class="text-center">募集中</th>
                        <th class="text-center">開催</th>
                        <th class="text-center">議事録</th>
                        <th class="text-center">編集</th>
                    </tr>
                    <c:forEach var="meeting" items="${meetings}" varStatus="status">
                        <c:if test="${meeting.delete_flag == AttributeConst.MET_DELETE_FALSE.getIntegerValue()}">
                            <tr class="row${status.count % 2}">
                                <td><c:out value="${meeting.date}" /></td>
                                <td><c:out value="${meeting.name}" /></td>
                                <td><a href="<c:url value='?action=${actAge}&command=${commIndex}&meeting_id=${meeting.id}' />" >議題へ</a></td>
                                <td><a href="<c:url value='?action=${actWan}&command=${commIndexMee}&meeting_id=${meeting.id}' />" >募集へ</a></td>
                                <td><a href="<c:url value='?action=${actMee}&command=${commSel}&meeting_id=${meeting.id}' />" >開催する</a></td>
                                <td><a href="<c:url value='?action=${actMin}&command=${commShow}&meeting_id=${meeting.id}' />" >議事録へ</a></td>
                                <td><a href="<c:url value='?action=${actMee}&command=${commEdit}&meeting_id=${meeting.id}' />" >編集する</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:param>
</c:import>