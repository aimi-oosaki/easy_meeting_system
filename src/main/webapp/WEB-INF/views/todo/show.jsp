<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actProject" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="actTodo" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actIdea" value="${ForwardConst.ACT_IDE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commCreate" value="${ForwardConst.CMD_CREATE.getValue()}" /> <!-- ★追記 -->
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" /> <!-- ★追記 -->

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
            <div class="todo_wrapper">
                <h2>TO DO 詳細</h2>
                <br />
                <table class="table table-todo">
                    <tbody>
                        <tr>
                            <th>会議日</th>
                            <td align="left"><div class="text-left"><c:out value="${todo.meeting.date}" /></div></td>
                        </tr>
                        <tr>
                            <th>会議名</th>
                            <td  class="text-left"><c:out value="${todo.meeting.name}" /></td>
                        </tr>
                        <tr>
                            <th>担当</th>
                            <td  class="text-left"><c:out value="${todo.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>やること</th>
                            <td  class="text-left"><c:out value="${todo.what}" /></td>
                        </tr>
                        <tr>
                            <th>いつまで</th>
                            <td  class="text-left"><c:out value="${todo.deadline}" /></td>
                        </tr>
                        <tr>
                            <th>完了</th>
                                <c:choose>
                                <c:when test="${todo.status == AttributeConst.TODO_STATUS_FALSE.getIntegerValue()}">
                                   <td class="text-left"><p>未</p></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="text-left"><p>完了</p></td>
                                </c:otherwise>
                                </c:choose>
                        </tr>
                        <tr>
                            <th>結果</th>
                            <td class="text-left"><c:out value="${todo.consequence}" /></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <p>
                <a href="<c:url value='?action=${actTodo}&command=${commIdx}' />">一覧に戻る</a>
            </p>
        </div>
    </c:param>
</c:import>