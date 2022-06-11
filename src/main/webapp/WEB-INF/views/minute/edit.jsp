<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
        <h2 class="min-edi-title"><i class="fa-solid fa-pen-to-square"></i> 編集</h2>
        <p>
            <a class="delete min-edi-title min-del" href="#" onclick="confirmDestroy();"><i class="fa-solid fa-trash-can icon"></i></a>
        </p>
        <br />
        <form method="POST" action="<c:url value='?action=${actMin}&command=${commUpd}' />">
            <c:import url="_form.jsp" />
        </form>
        <br />
        <form method="POST"
            action="<c:url value='?action=${actMin}&command=${commDel}' />">
            <input type="hidden" name="${AttributeConst.EMP_ID.getValue()}" value="${employee.id}" />
            <input type="hidden" name="${AttributeConst.MIN_ID.getValue()}" value="${minute.id}" />
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
            <a href="<c:url value='?action=Project&command=index' />">一覧に戻る</a>
        </p>
        </div>
    </c:param>
</c:import>