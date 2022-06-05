<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <div id="top-btn">
          <input class="input_btn" type="button" onclick="location.href='<c:url value='?action=${actEmp}&command=${commNew}' />'" value="新規登録">
        </div>
        <table id="employee_list" class="table">
            <tbody>
                <tr>
                    <th class="text-center">社員番号</th>
                    <th class="text-center">氏名</th>
                    <th class="text-center">チーム</th>
                    <th class="text-center">詳細</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td><c:out value="${employee.code}" /></td>
                            <td><c:out value="${employee.name}" /></td>
                            <td><c:out value="${employee.team.name}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${employee.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                        （削除済み）
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='?action=${actEmp}&command=${commShow}&id=${employee.id}' />">詳細</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </c:param>
</c:import>