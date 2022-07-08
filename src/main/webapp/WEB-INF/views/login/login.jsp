<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="command" value="${ForwardConst.CMD_LOGIN.getValue()}" />
<c:set var="guestCom" value="${ForwardConst.CMD_GUEST_LOGIN.getValue()}" />

<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>ジタン会議</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
    <div class="signup_wrapper">
        <c:if test="${loginError}">
            <div id="flush_error">
                社員番号かパスワードが間違っています。
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ログイン</h2>
        <div class="signin">
            <form method="POST" action="<c:url value='/?action=${action}&command=${command}' />">
                <label for="${AttributeConst.EMP_COM_ID.getValue()}">会社ID</label><br />
                <input style="text-align: left;" type="text" name="${AttributeConst.EMP_COM_ID.getValue()}" value="${company_id}" />
                <br /><br />

                <label for="${AttributeConst.EMP_CODE.getValue()}">社員番号</label><br />
                <input style="text-align: left;" type="text" name="${AttributeConst.EMP_CODE.getValue()}" value="${code}" />
                <br /><br />

                <label for="${AttributeConst.EMP_PASS.getValue()}">パスワード</label><br />
                <input style="text-align: left;" type="password" name="${AttributeConst.EMP_PASS.getValue()}" />
                <br /><br />

                <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                <button class="login-btn" type="submit">ログイン</button>
            </form>
            <br />
            <form method="POST" action="<c:url value='/?action=${action}&command=${guestCom}' />">
                <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                <button class="login-btn" type="submit">ゲストとしてログイン</button>
            </form>
        </div>
        <div id="footer">
        </div>

        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    </div>
    </body>
</html>