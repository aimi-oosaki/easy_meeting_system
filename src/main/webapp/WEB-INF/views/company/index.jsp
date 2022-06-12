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
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <h2>募集 一覧</h2>
        <table class="table table-striped">
            <tbody>
                <tr>
                    <th>会社名</th>
                    <th>詳細</th>
                    <th>編集</th>
                </tr>
                <c:forEach var="company" items="${companies}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${company.name}" /></td>
                        <td><a href="<c:url value='?action=${actCom}&command=${commShow}&company_id=${company.id}' />" >詳細</a></td>
                        <td><a href="<c:url value='?action=${actCom}&command=${commEdit}&company_id=${company.id}' />" >編集</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </c:param>
</c:import>