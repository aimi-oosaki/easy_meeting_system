<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actCom" value="${ForwardConst.ACT_COM.getValue()}" />
<c:set var="actProject" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="actIdea" value="${ForwardConst.ACT_IDE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commCreate" value="${ForwardConst.CMD_CREATE.getValue()}" /> <!-- ★追記 -->
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" /> <!-- ★追記 -->

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <h2>募集 詳細</h2>

        <table>
            <tbody>
                <tr>
                    <th>会社名</th>
                    <td><c:out value="${company.name}" /></td>
                </tr>
            </tbody>
        </table>

        <br /><br />

        <p>
            <a href="<c:url value='?action=${actCom}&command=${commIdx}' />">一覧に戻る</a>
        </p>
        </div>
    </c:param>
</c:import>