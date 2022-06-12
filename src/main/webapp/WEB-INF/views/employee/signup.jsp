<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="action" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE_EMP.getValue()}" />

<c:set var="actHom" value="${ForwardConst.ACT_HOME.getValue()}" />
<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actTodo" value="${ForwardConst.ACT_TOD.getValue()}" />

<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />

<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>ジタン会議</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
        <!--FontAwesomeの読み込み-->
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLwvnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
        <!-- BootstrapのCSS読み込み -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
    <div class="signup_wrapper">
        <h2>新規登録</h2>
        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}&_token=${_token}' />">
            <c:if test="${errors != null}">
                <div id="flush_error">
                    入力内容にエラーがあります。<br />
                    <c:forEach var="error" items="${errors}">
                        ・<c:out value="${error}" /><br />
                    </c:forEach>
                </div>
            </c:if>

            <label for="${AttributeConst.EMP_COM.getValue()}">会社コード</label><br />
            <input type="text" name="${AttributeConst.EMP_COM.getValue()}"  />
            <br /><br />

            <label for="${AttributeConst.TEA_NAME.getValue()}">チーム名（部署名）</label><br />
            <input type="text" name="${AttributeConst.TEA_NAME.getValue()}"  />
            <br /><br />

            <label for="${AttributeConst.EMP_CODE.getValue()}">社員番号</label><br />
            <input type="text" name="${AttributeConst.EMP_CODE.getValue()}" />
            <br /><br />

            <label for="${AttributeConst.EMP_NAME.getValue()}">氏名</label><br />
            <input type="text" name="${AttributeConst.EMP_NAME.getValue()}" value="${employee.name}" />
            <br /><br />

            <label for="${AttributeConst.EMP_PASS.getValue()}" >パスワード</label><br />
            <input type="password" name="${AttributeConst.EMP_PASS.getValue()}" />
            <br /><br />

            <label for="${AttributeConst.EMP_ADMIN_FLG.getValue()}">権限</label><br />
            <select name="${AttributeConst.EMP_ADMIN_FLG.getValue()}">
                <option value="${AttributeConst.ROLE_GENERAL.getIntegerValue()}" >一般</option>
                <option value="${AttributeConst.ROLE_ADMIN.getIntegerValue()}" selected>管理者</option>
            </select>
            <br /><br />

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">投稿</button>
        </form>

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