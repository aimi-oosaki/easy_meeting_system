<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTea" value="${ForwardConst.ACT_TEA.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div id="content-wrapper">
            <div id="team-form">
                <form method="POST" action="<c:url value='?action=${actTea}&command=${commUpd}' />">
                    <c:import url="_form.jsp" />
                </form>

                <p>
                    <a class="delete" href="#" onclick="confirmDestroy();"><i class="fa-solid fa-trash-can icon"></i></a>
                </p>
                <form method="POST"
                    action="<c:url value='?action=${actTea}&command=${commDel}' />">
                    <input type="hidden" name="${AttributeConst.EMP_ID.getValue()}" value="${employee.id}" />
                    <input type="hidden" name="${AttributeConst.TEA_ID.getValue()}" value="${team.id}" />
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                </form>
                <script>
                    function confirmDestroy() {
                        if (confirm("本当に削除してよろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>
                <br />
                <p>
                    <a href="<c:url value='?action=Team&command=index' />">一覧に戻る</a>
                </p>
            </div>
        </div>
    </c:param>
</c:import>