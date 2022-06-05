<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="action" value="${ForwardConst.ACT_AGE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>議題　新規登録</h2>

        <%-- エラー表示 --%>
        <c:if test="${errors != null}">
            <div id="flush_error">
                入力内容にエラーがあります。<br />
                <c:forEach var="error" items="${errors}">
                    ・<c:out value="${error}" /><br />
                </c:forEach>

            </div>
        </c:if>

        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
        </form>

        <%-- 行を追加するJava Script --%>
        <script type="text/javascript">
        $(function() {
            $('#add').click(function(){
                  var tr_form = '' +
                  '<tr>' +
                      '<td><input type="text" name="${AttributeConst.AGE_TITLE.getValue()}[]">' +
//                       '<input type="hidden" name="${AttributeConst.AGE_ID.getValue()}[]" value="${agenda.id}" />' +
                      '</td>' +
                  '</tr>';
                  $(tr_form).appendTo($('table > tbody'));
           });
        });
        </script>

        <p><a href="<c:url value='?action=Meeting&command=${commIdx}' />">一覧に戻る</a></p>
    </c:param>
</c:import>