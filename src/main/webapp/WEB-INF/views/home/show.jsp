<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIndex" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
    <div class="content-wrapper">
        <%-- 画像メニュー --%>
        <div id="home_menus_wrapper">
            <div class="top_menus">
                <div class="top-menu">
                    <a href="<c:url value='?action=Meeting&command=${commNew}' />"><img class="home_img" src="/easy_meeting_system/img/newMeeting.png"></a>
                    <a href="<c:url value='?action=Meeting&command=${commNew}' />" class="link-secondary text-decoration-none">新しい会議を作成</a>
                </div>
                <div class="top-menu">
                   <a href="<c:url value='?action=Meeting&command=${commIndex}' />" class="top_menu"><img class="home_img" src="/easy_meeting_system/img/meetingIndex.png"></a>
                    <a href="<c:url value='?action=Meeting&command=${commIndex}' />" class="link-secondary text-decoration-none">会議一覧</a>
                </div>
                <div class="top-menu">
                    <a href="<c:url value='?action=Todo&command=${commIndex}' />" class="top_menu"><img class="home_img" src="/easy_meeting_system/img/todo.png"></a>
                    <a href="<c:url value='?action=Todo&command=${commIndex}' />" class="link-secondary text-decoration-none">TO DOリスト</a>
                </div>
            </div>
        </div>

        <%-- アイデア募集中テーブル --%>
        <div class="home_want_wrapper">
            <h2 id="idea_list_home">＼ アイデア募集中 ／</h2>
            <input class="input_btn" type="button" onclick="location.href='<c:url value='?action=${actWan}&command=${commNew}' />'" value="募集投稿">
            <br/><br/>
            <table id="want_list" class="table">
                <tbody>
                    <tr>
                        <th class="text-center" scope="row">締切</th>
                        <th class="text-center">タイトル</th>
                        <th class="text-center">内容</th>
                    </tr>
                    <c:forEach var="want" items="${wants}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td><c:out value="${want.due_date}" /></td>
                            <td><a href="<c:url value='?action=${actWan}&command=${commShow}&want_id=${want.id}' />"><c:out value="${want.title}" /></a></td>
                            <td><a href="<c:url value='?action=${actWan}&command=${commShow}&want_id=${want.id}' />"><c:out value="${want.content}" /></a></td>                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    </c:param>
</c:import>