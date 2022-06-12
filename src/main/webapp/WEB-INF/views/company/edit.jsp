<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actCom" value="${ForwardConst.ACT_COM.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <h2>会社編集</h2>
        <form method="POST" action="<c:url value='?action=${actCom}&command=${commUpd}' />">
            <c:import url="_form.jsp" />
        </form>

        <p>
            <a href="#" onclick="confirmDestroy();">削除する</a>
        </p>
        <form method="POST"
            action="<c:url value='?action=${actCom}&command=${commDel}' />">
            <input type="hidden" name="${AttributeConst.EMP_ID.getValue()}" value="${employee.id}" />
            <input type="hidden" name="${AttributeConst.COM_ID.getValue()}" value="${company.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        </form>
        <script>
            function confirmDestroy() {
                if (confirm("本当に削除してよろしいですか？")) {
                    document.forms[1].submit();
                }
            }
        </script>

        <p>
            <a href="<c:url value='?action=Company&command=index' />">一覧に戻る</a>
        </p>
        </div>
    </c:param>
</c:import>