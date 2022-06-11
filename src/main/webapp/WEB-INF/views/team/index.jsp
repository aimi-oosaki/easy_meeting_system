<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="actCom" value="${ForwardConst.ACT_COM.getValue()}" />
<c:set var="actTea" value="${ForwardConst.ACT_TEA.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <div id="top-btn">
          <input class="input_btn" type="button" onclick="location.href='<c:url value='?action=${actTea}&command=${commNew}' />'" value="新規登録">
        </div>
        <table class="table">
            <tbody>
                <tr>
                    <th class="text-center">チーム名</th>
                    <th class="text-center">編集</th>
                </tr>
                <c:forEach var="team" items="${teams}" varStatus="status">
                    <tr class="row${status.count % 2}">
<%--                         <td><a href="<c:url value='?action=${actTea}&command=${commShow}&team_id=${team.id}' />"><c:out value="${team.name}" /></a></td> --%>
                        <td><c:out value="${team.name}" /></td>                        <c:choose>
                            <c:when test="${team.delete_flag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                <td>（削除済み）</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="<c:url value='?action=${actTea}&command=${commEdit}&team_id=${team.id}' />" >編集</a></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </c:param>
</c:import>