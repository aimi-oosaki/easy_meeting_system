<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<table>
    <thead>
        <th class="text-center">だれが</th>
        <th class="text-center">いつまでに</th>
        <th class="text-center">なにする？</th>
        <th class="text-center">完了</th>
        <th class="text-center">結果</th>
    </thead>
    <tbody>
        <tr>
            <td>
                <select id="todo_who" name="${AttributeConst.TODO_EMP.getValue()}">
                    <option>担当者を選んでください</option>
                    <c:forEach var="employee" items="${employees}">
                        <option value="${employee.id}" <c:if test="${employee.id == todo.employee.id}">selected</c:if>>${employee.name}</option>

                    </c:forEach>
                </select>
            </td>
            <td>
               <fmt:parseDate value="${todo.deadline}" pattern="yyyy-MM-dd" var="dueDay" type="date" />
                <input type="date" name="${AttributeConst.TODO_DEADLINE.getValue()}" value="<fmt:formatDate value='${dueDay}' pattern='yyyy-MM-dd' />" />
            </td>
            <td>
                <input type="text" name="${AttributeConst.TODO_WHAT.getValue()}" value="${todo.what}" />
            </td>
            <td>
                <select name="${AttributeConst.TODO_STATUS.getValue()}">
                    <option value="${AttributeConst.TODO_STATUS_FALSE.getIntegerValue()}"<c:if test="${todo.status == AttributeConst.TODO_STATUS_FALSE.getIntegerValue()}"> selected</c:if>>未</option>
                    <option value="${AttributeConst.TODO_STATUS_TRUE.getIntegerValue()}"<c:if test="${todo.status == AttributeConst.TODO_STATUS_TRUE.getIntegerValue()}"> selected</c:if>>完了</option>
                </select>
            </td>
            <td>
                <input type="text" name="${AttributeConst.TODO_CON.getValue()}" value="${todo.consequence}">
            </td>
        </tr>
    </tbody>
</table>

<input type="hidden" name="${AttributeConst.MET_ID.getValue()}" value="${meeting.id}" />
<input type="hidden" name="${AttributeConst.TODO_ID.getValue()}" value="${todo.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />

<br />
<button type="submit"  class="submit-btn">更新</button>
