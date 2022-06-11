<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commCreate" value="${ForwardConst.CMD_CREATE.getValue()}" /> <!-- ★追記 -->
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" /> <!-- ★追記 -->

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <div id="emp-top-wrapper">
            <div id="emp-top-left">
                <h2><c:out value="${employee.name}" /></h2>
            </div>
            <div id="emp-top-right">
                <a href="<c:url value='?action=${actEmp}&command=${commEdit}&id=${employee.id}' />">編集</a>
            </div>
        </div>
        <table class="table">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <td><c:out value="${employee.code}" /></td>
                </tr>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${employee.name}" /></td>
                </tr>
                <tr>
                    <th>権限</th>
                    <td><c:choose>
                            <c:when test="${employee.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}">管理者</c:when>
                            <c:otherwise>一般</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- ★追記 -->
        <c:if test="${sessionScope.login_employee.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}">
        <!-- 管理者の場合のみ従業員情報編集を表示 -->
        </c:if>
        <p>
            <a href="<c:url value='?action=${actEmp}&command=${commIdx}' />">一覧に戻る</a>
        </p>
        </div>
    </c:param>
</c:import>