<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actWant" value="${ForwardConst.ACT_WAN.getValue()}" />
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
                    <th>会議名</th>
                    <td><c:out value="${want.meeting.name}" /></td>
                </tr>
                <tr>
                    <th>タイトル</th>
                    <td><c:out value="${want.title}" /></td>
                </tr>
                <tr>
                    <th>内容</th>
                    <td><c:out value="${want.content}" /></td>
                </tr>
                <tr>
                    <th>締切</th>
                    <td><c:out value="${want.due_date}" /></td>
                </tr>
            </tbody>
        </table>

        <br /><br />
        <input class="input_btn" type="button" onclick="location.href='<c:url value='?action=${actIdea}&command=${commNew}&want_id=${want.id}' />'" value="アイデアを投稿">
        <br /><br />
        <table>
            <tbody>
                <tr>
                    <th class="text-center">回答</th>
                    <th class="text-center"></th>
                </tr>
                <c:forEach var="idea" items="${ideas}" varStatus="status">
                     <tr class="row${status.count % 2}">
                         <td><c:out value="${idea.content}" /></td>
                         <c:if test="${sessionScope.login_employee.id == idea.employee.id}">
                            <td><a href="<c:url value='?action=${actIdea}&command=${commEdit}&idea_id=${idea.id}' />">編集</a></td>
                         </c:if>
                     </tr>
                </c:forEach>
            </tbody>
        </table>
        <br />
        <p>
            <a href="<c:url value='?action=${actWant}&command=${commIdx}' />">一覧に戻る</a>
        </p>
        </div>
    </c:param>
</c:import>