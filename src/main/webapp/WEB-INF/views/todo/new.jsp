<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="action" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
<c:param name="content">
<div id="content-wrapper">
<h2>TO DO 作成</h2>

<%-- エラー表示 --%>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>

<%-- フォーム --%>
<form method="POST" action="<c:url value='?action=${action}&command=${commCrt}&meeting_id=${meeting.id}' />">
    <table>
        <thead>
            <tr>
                <th class="text-center">だれが</th>
                <th class="text-center">いつまでに</th>
                <th class="text-center">なにする？</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <select id="todo_who" name="${AttributeConst.TODO_EMP.getValue()}[]">
                        <option selected>担当者を選んでください</option>
                        <c:forEach var="employee" items="${employees}">
                            <option value="${employee.id}">${employee.name}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                   <fmt:parseDate value="${todo.deadline}" pattern="yyyy-MM-dd" var="dueDay" type="date" />
                    <input type="date" name="${AttributeConst.TODO_DEADLINE.getValue()}[]" value="<fmt:formatDate value='${deadline}' pattern='yyyy-MM-dd' />" />
                </td>
                <td>
                    <input type="text" name="${AttributeConst.TODO_WHAT.getValue()}[]" value="${todo.what}" />
                </td>
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <td ><button id="add" class="btn_add" type="button">＋</button></td>
            </tr>
        </tfoot>
    </table>

    <input type="hidden" name="${AttributeConst.MET_ID.getValue()}" value="${meeting.id}" />
    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
    <br />
    <button type="submit" id="btn_todo_create"  class="submit-btn">作成</button>
</form>

<%-- 行を追加するJava Script --%>
<script type="text/javascript">
$(function() {

    $('#add').click(function(){
          var tr_form = '' +
          '<tr>' +
              '<td><select id="todo_who" name="${AttributeConst.TODO_EMP.getValue()}[]">' +
                       '<option selected>担当者を選んでください</option>' +
                       '<c:forEach var="employee" items="${employees}">' +
                         '<option value="${employee.id}">${employee.name}</option>' +
                       '</c:forEach>' +
              '</select></td>' +
              '<td><input type="date" name="${AttributeConst.TODO_DEADLINE.getValue()}[]"></td>' +
              '<td><input type="text" name="${AttributeConst.TODO_WHAT.getValue()}[]"></td>' +
          '</tr>';
          $(tr_form).appendTo($('table > tbody'));
   });
});
</script>
</div>
</c:param>
</c:import>

