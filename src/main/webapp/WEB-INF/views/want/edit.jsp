<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
            <div id="want-edit">
                <h2><i class="fa-solid fa-pen-to-square"></i> 編集</h2>
                <form method="POST" action="<c:url value='?action=${actWan}&command=${commUpd}' />">
                    <c:import url="_form.jsp" />
                </form>

                <p>
                    <a class="delete" href="#" onclick="confirmDestroy();"><i class="fa-solid fa-trash-can icon"></i></a>
                </p>
                <form method="POST"
                    action="<c:url value='?action=${actWan}&command=${commDel}' />">
                    <input type="hidden" name="${AttributeConst.EMP_ID.getValue()}" value="${employee.id}" />
                    <input type="hidden" name="${AttributeConst.WAN_ID.getValue()}" value="${want.id}" />
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
                    <a href="<c:url value='?action=Want&command=index' />">一覧に戻る</a>
                </p>
            </div>
        </div>
    </c:param>
</c:import>