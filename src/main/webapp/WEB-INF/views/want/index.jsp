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
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIndex" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
   <c:param name="content">
        <div id="content-wrapper">
        <h2></h2>
        <c:choose>
            <c:when test="${empty wants}">
                <div class="nothing">
                    <img class="home_img" src="/img/nothing.png">
                    <h2>まだ作成されていません</h2>
                    <br />
                    <input class="submit-btn nothing-btn" type="button" onclick="location.href='<c:url value='?action=${actWan}&command=${commNew}' />'" value="作成">
                    <br /><br />
<%--                     <a href="<c:url value='?action=${actMee}&command=${commIndex}' />" >一覧に戻る</a> --%>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <tbody>
                        <tr>
                            <th class="text-center">会議名</th>
                            <th class="text-center">タイトル</th>
                            <th class="text-center">締切</th>
                            <th class="text-center"></th>
                        </tr>
                        <c:forEach var="want" items="${wants}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td><c:out value="${want.meeting.name}" /></td>
                                <td class="overflow"><a href="<c:url value='?action=${actWan}&command=${commShow}&want_id=${want.id}' />" ><c:out value="${want.title}" /></a></td>
                                <td><c:out value="${want.due_date}" /></td>
                                <td><a href="<c:url value='?action=${actWan}&command=${commEdit}&want_id=${want.id}' />" >編集</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
        </div>
    </c:param>
</c:import>