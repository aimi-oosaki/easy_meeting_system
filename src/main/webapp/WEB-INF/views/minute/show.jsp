<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commCreate" value="${ForwardConst.CMD_CREATE.getValue()}" />
<!-- ★追記 -->
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<!-- ★追記 -->

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${empty minute}">
            <!-- 議事録がない時 -->
                <div class="nothing">
                    <img class="home_img" src="/meeting_system/img/nothing.png">
                    <h2>まだ作成されていません</h2>
                    <br />
                    <div class="center-btn">
                        <input class="submit-btn nothing-btn" type="button"
                            onclick="location.href='<c:url value='?action=${actMin}&command=${commNew}&meeting_id=${meeting.id}' />'"
                            value="作成">
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <!-- 議事録がある時 -->
                <div id="content-wrapper">
                    <div id="min_top_wrapper">
                        <div id="min-top-left">
                            <p class="min-title">議事録</p>
                            <p>
                                <c:out value="${meeting.date}" />
                            </p>
                            <p id="meeting-name">
                                <c:out value="${meeting.name}" />
                            </p>
                        </div>
                        <div id="min-top-right">
                            <div id="min-attendee">
                                <p>参加者</p>
                                <c:out value="${minute.attendees}" />
                            </div>
                            <p id="date">
                                <c:out value="作成者: ${minute.employee.name}" />
                            </p>
                        </div>
                    </div>

                    <div id="min-age">
                        <p class="min-title">議題</p>
                        <table>
                            <tbody>
                                <c:forEach var="agenda" items="${agendas}">
                                    <tr>
                                        <th>${agenda.title}</th>
                                        <td>${agenda.summary}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div id="min-dec">
                        <p class="min-title">決定事項</p>
                        <c:out value="${minute.decided}" />
                    </div>

                    <div id="min-pen">
                        <p class="min-title">保留事項</p>
                        <c:out value="${minute.pending}" />
                    </div>

                    <br />
                    <p>
                        <a
                            href="<c:url value='?action=${actMin}&command=${commEdit}&minute_id=${minute.id}' />">編集</a>
                    </p>

                    <p>
                        <a href="<c:url value='?action=${actMee}&command=${commIdx}' />">一覧に戻る</a>
                    </p>
            </c:otherwise>
        </c:choose>
        </div>
    </c:param>
</c:import>