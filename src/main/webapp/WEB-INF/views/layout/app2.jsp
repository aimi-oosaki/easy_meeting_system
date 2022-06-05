<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actHom" value="${ForwardConst.ACT_HOME.getValue()}" />
<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actTodo" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actTeam" value="${ForwardConst.ACT_TEA.getValue()}" />

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
        <script src="https://kit.fontawesome.com/d69d5f4184.js" crossorigin="anonymous"></script>
        <!-- BootstrapのCSS読み込み -->
<!--         <link href="css/bootstrap.min.css" rel="stylesheet"> -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <!-- jQuery読み込み -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- BootstrapのJS読み込み -->
        <script src="js/bootstrap.min.js"></script>
        <!-- ドロップダウンメニューのBootstrapのJS読み込み -->
        <script src="http://twitter.github.com/bootstrap/1.3.0/bootstrap-dropdown.js"></script>
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>

    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

        <div id="head_wrapper">
            <div id="header">
                <div id="header_conteiner">
                    <div class="header_left">
                        <a href="<c:url value='?action=${actHom}&command=${commShow}' />">
                           <img class="logo" src="/meeting_system/img/logo.png">
                        </a>
                    </div>

                    <div class="header_menus">
                        <ul>
<%--                             <li><a href="<c:url value='?action=${actHom}&command=${commShow}' />" class="menu link-secondary text-decoration-none">HOME</a></li> --%>
<!--                             <li class="dropdown" data-dropdown="dropdown"> -->
<!--                                 <a href="" class="dropdown-toggle link-secondary text-decoration-none">会議</a> -->
<!--                                 <ul class="dropdown-menu"> -->
<%--                                 <li><a href="<c:url value='?action=${actMee}&command=${commNew}' />" class="menu link-secondary text-decoration-none">会議作成</a></li> --%>
<%--                                 <li><a href="<c:url value='?action=${actMee}&command=${commIdx}' />" class="menu link-secondary text-decoration-none">会議一覧</a></li> --%>
<!--                                 </ul> -->
<!--                             </li> -->
                            <li><a href="<c:url value='?action=${actMee}&command=${commNew}' />" class="menu link-secondary text-decoration-none">会議作成</a></li>
                            <li><a href="<c:url value='?action=${actMee}&command=${commIdx}' />" class="menu link-secondary text-decoration-none">会議一覧</a></li>
                            <li><a href="<c:url value='?action=${actTodo}&command=${commIdx}' />" class="menu link-secondary text-decoration-none">TO DOリスト</a></li>
                            <li><a href="<c:url value='?action=${actTeam}&command=${commIdx}' />" class="menu link-secondary text-decoration-none">チーム管理</a></li>
                            <c:if test="${sessionScope.login_employee != null}">
                                <c:if test="${sessionScope.login_employee.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}">
                                    <li><a href="<c:url value='?action=${actEmp}&command=${commIdx}' />" class="menu link-secondary text-decoration-none">従業員管理</a></li>
                                </c:if>
                            </c:if>
                            <li><a href="<c:url value='?action=${actTeam}&command=${commIdx}' />" class="menu link-secondary text-decoration-none"><c:out value="${sessionScope.login_employee.name}" /></a></li>
                            <li><a href="<c:url value='?action=${actAuth}&command=${commOut}' />" class="menu link-secondary text-decoration-none">ログアウト</a></li>
                        </ul>
                    </div>
                </div>
                <div id="content">${param.content}</div>
            </div>
            <div id="footer">
            </div>
        </div>
    </body>
</html>