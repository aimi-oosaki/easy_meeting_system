<%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
      <c:param name="content">
      <div id="content-wrapper">
              <h2>まだ議事録は作成されていません。</h2>
              <a href="<c:url value='?action=${actMin}&command=${commNew}&meeting_id=${meeting.id}' />" >議事録へ</a>
      </div>
      </c:param>
</c:import>