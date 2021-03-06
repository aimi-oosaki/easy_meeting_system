<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actAge" value="${ForwardConst.ACT_AGE.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />


<c:import url="../layout/app.jsp">
   <c:param name="content">
   <div id="content-wrapper">
        <div id="agenda-wrapper">
<!--         <h2>議題</h2> -->
            <c:choose>
                <c:when test="${empty agendas}">
                    <div class="nothing">
                        <img class="home_img" src="/img/nothing.png">
                        <h2>まだ作成されていません</h2>
                        <br />
                        <div class="center-btn"><input class="submit-btn nothing-btn" type="button" onclick="location.href='<c:url value='?action=${actAge}&command=${commNew}&meeting_id=${meeting.id}' />'" value="作成"></div>
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <tbody>
                            <c:forEach var="agenda" items="${agendas}" varStatus="status">
                                <!-- 未完了のTO DOなら表示 -->
                                <tr class="row${status.count % 2}">
                                    <td><c:out value="${status.count}" /></td>
                                    <td><c:out value="${agenda.title}" /></td>
                                    <td><a href="<c:url value='?action=${actAge}&command=${commEdit}&agenda_id=${agenda.id}&meeting_id=${meeting.id}' />" >編集</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                   <input class="submit-btn" type="button" onclick="location.href='<c:url value='?action=${actAge}&command=${commNew}&meeting_id=${meeting.id}' />'" value="追加">
                </c:otherwise>
            </c:choose>
            <br /><br />
            <p><a href="<c:url value='?action=${actMee}&command=${commIdx}' />">一覧に戻る</a></p>
        </div>
    </div>
    </c:param>
</c:import>