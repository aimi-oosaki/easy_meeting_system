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

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <h2>TO DOリスト</h2>
        <table class="table" id="targetTable">
            <tbody>
                <tr>
                    <th class="text-center">完了</th>
                    <th class="text-center">だれが</th>
                    <th class="text-center">いつまでに</th>
                    <th class="text-center">なにを</th>
                    <th class="text-center">どうなった</th>
                    <th class="text-center"></th>
                </tr>
                <c:forEach var="todo" items="${todos}" varStatus="status">
                        <!-- 未完了のTO DOなら表示 -->
                        <tr class="row${status.count % 2}">
                            <c:if test="${todo.status == AttributeConst.TODO_STATUS_FALSE.getIntegerValue()}">
                                <td></td>
                            </c:if>
                            <c:if test="${todo.status == AttributeConst.TODO_STATUS_TRUE.getIntegerValue()}">
                                <td><i class="fa-regular fa-circle-check"></i></td>
                            </c:if>
                            <td><c:out value="${todo.employee.name}" /></td>
                            <td><c:out value="${todo.deadline}" /></td>
                            <td class="overflow"><a href="<c:url value='?action=${actTod}&command=${commShow}&todo_id=${todo.id}' />"><c:out value="${todo.what}" /></a></td>
                            <td><c:out value="${todo.consequence}" /></td>
                            <td><a href="<c:url value='?action=${actTod}&command=${commEdit}&todo_id=${todo.id}&meeting_id=${todo.meeting.id}' />" >編集</a></td>
                        </tr>

                </c:forEach>
            </tbody>
        </table>
<!--         <div id="text-button"><p id="text">クリック</p></div> -->
        </div>
    </c:param>
</c:import>

<!-- Tableの内容を変更するJS -->
<script>
document.getElementById("text-button").onclick = function() {
    let table = document.getElementById('targetTable');
    var todos = new Array();

    for (i=1; i<3; i++){
        <c:forEach var="todo" items="${todos}" varStatus="status">
            table.rows[i].cells[0].innerText = '${todo.employee.name}';
            table.rows[i].cells[1].innerText = '${todo.deadline}';
            table.rows[i].cells[2].innerText = '${todo.what}';
            table.rows[i].cells[3].innerText = '${todo.consequence}';
        </c:forEach>
    }
};
</script>