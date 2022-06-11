<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actAge" value="${ForwardConst.ACT_AGE.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <div id="content-wrapper">
        <div id="agenda-wrapper">
            <h2><i class="fa-solid fa-pen-to-square"></i> 編集</h2>
            <!-- 議題 -->
            <div class="age-edi-container">
               <form method="POST" action="<c:url value='?action=${actAge}&command=${commUpd}' />">
                    <br />
                    <input type="text" name="${AttributeConst.AGE_TITLE.getValue()}[]" value="${agenda.title}" />
                    <input type="hidden" name="${AttributeConst.AGE_ID.getValue()}[]" value="${agenda.id}" />
                    <input type="hidden" name="${AttributeConst.MET_ID.getValue()}" value="${meeting.id}" />
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <br /><br />
                    <button type="submit" class="submit-btn">更新</button>
              </form>
                    <%-- 削除機能 --%>
                    <p>
                        <a class="delete" href="#" onclick="confirmDestroy();"><i class="fa-solid fa-trash-can icon"></i></a>
                    </p>
           </div>
           <form method="POST"
               action="<c:url value='?action=${actAge}&command=${commDel}&agenda_id=${agenda.id}' />">
               <input type="hidden" name="${AttributeConst.EMP_ID.getValue()}" value="${employee.id}" />
               <input type="hidden" name="${AttributeConst.AGE_ID.getValue()}" value="${agenda.id}" />
               <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
           </form>
           <script>
               function confirmDestroy() {
                   if (confirm("本当に削除してよろしいですか？")) {
                       document.forms[1].submit();
                   }
               }
           </script>

            <br /><br />
            <p>
                <a href="<c:url value='?action=Meeting&command=index' />">一覧に戻る</a>
            </p>
        </div>
    </div>
    </c:param>
</c:import>