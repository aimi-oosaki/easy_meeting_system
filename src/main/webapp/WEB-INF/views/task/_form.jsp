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

<div class="task_form">
    <label  for="${AttributeConst.TAS_TITLE.getValue()}">プロジェクト名</label><br />
    <input class="task-edit" type="text" name="${AttributeConst.TAS_TITLE.getValue()}" value="${task.title}" />
    <br /><br />

    <label  for="${AttributeConst.EMPLOYEE.getValue()}">担当者</label><br />
    <select class="task_who" name="${AttributeConst.EMPLOYEE.getValue()}">
        <c:forEach var="employee" items="${employees}">
            <option value="${employee.id}" <c:if test="${employee.id == task.employee.id}">selected</c:if>>${employee.name}</option>
        </c:forEach>
    </select>
    <br /><br />

    <fmt:parseDate value="${task.due_date}" pattern="yyyy-MM-dd" var="dueDay" type="date" />
    <label for="${AttributeConst.TAS_DUEDATE.getValue()}">期日</label><br />
    <input class="task-edit" type="date" name="${AttributeConst.TAS_DUEDATE.getValue()}" value="<fmt:formatDate value='${dueDay}' pattern='yyyy-MM-dd' />" />
    <br /><br />

    <label for="${AttributeConst.TAS_PROGRESS.getValue()}">進捗状況</label><br />
    <select id="task_progress" name="${AttributeConst.TAS_PROGRESS.getValue()}">
            <option value="1">順調（期日内に完了見込み）</option>
            <option value="2">注意（期日内での完了が微妙）</option>
            <option value="3">危険（期日内の完了は困難）</option>
    </select>

    <br /><br />

    <label for="${AttributeConst.TAS_PROBLEM.getValue()}">課題</label><br />
    <textarea name="${AttributeConst.TAS_PROBLEM.getValue()}" rows="10" cols="50">${task.problem}</textarea>
    <br /><br />

    <label for="${AttributeConst.TAS_SOLUTION.getValue()}">解決策・追加作業</label><br />
    <textarea name="${AttributeConst.TAS_SOLUTION.getValue()}" rows="10" cols="50">${task.solution}</textarea>
    <br /><br />

    <label for="${AttributeConst.TAS_STATUS.getValue()}">完了・未完了</label><br />
    <select id="task_progress" name="${AttributeConst.TAS_STATUS.getValue()}">
            <option value="0">未完了</option>
            <option value="1">完了（期日内での完了が微妙）</option>
    </select>
    <br /><br />
    <input type="hidden" name="${AttributeConst.TAS_ID.getValue()}" value="${task.id}" />
    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
    <button type="submit" class="submit-btn">投稿</button>
</div>