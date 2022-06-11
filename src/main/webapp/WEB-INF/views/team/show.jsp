<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actCom" value="${ForwardConst.ACT_COM.getValue()}" />
<c:set var="actTea" value="${ForwardConst.ACT_TEA.getValue()}" />
<c:set var="actIdea" value="${ForwardConst.ACT_IDE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commCreate" value="${ForwardConst.CMD_CREATE.getValue()}" /> <!-- ★追記 -->
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" /> <!-- ★追記 -->

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <div id="content-wrapper">
        <h2>チーム 詳細</h2>

        <table>
            <tbody>
                <tr>
                    <th>チーム名</th>
                    <td><c:out value="${team.name}" /></td>
                </tr>
            </tbody>
        </table>

        <br /><br />

        <p>
            <a href="<c:url value='?action=${actTea}&command=${commIdx}' />">一覧に戻る</a>
        </p>
        </div>
    </c:param>
</c:import>