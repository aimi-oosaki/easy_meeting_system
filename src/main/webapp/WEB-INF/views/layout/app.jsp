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
<c:set var="actTask" value="${ForwardConst.ACT_TAS.getValue()}" />

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
        <link rel="icon" href="<c:url value='/img/favicon.png' />">
        <!--FontAwesomeの読み込み-->
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLwvnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
        <script src="https://kit.fontawesome.com/d69d5f4184.js" crossorigin="anonymous"></script>
        <!-- BootstrapのCSS読み込み -->
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
        <!-- Googleフォントのダウンロード -->
        <link ref="stylesheet" href="https://fonts.googleapis.com/css?family=Architects+Daughter">
    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

        <!-- ヘッダー -->
        <div id="head_wrapper">
            <div id="header">
               <nav class="navbar navbar-expand-lg navbar-light bg-light .justify-content-end">
                 <a class="navbar-brand" href="<c:url value='?action=${actHom}&command=${commShow}' />"><img class="logo" src="/img/logo.png"></a>
                 <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                   <span class="navbar-toggler-icon"></span>
                 </button>

                 <div class="collapse navbar-collapse navbar-right" id="navbarSupportedContent">
                         <ul class="navbar-nav ml-auto">
                               <li class="nav-item active">
                                  <a class="nav-link" href="<c:url value='?action=${actHom}&command=${commShow}' />">Home <span class="sr-only">(current)</span></a>
                               </li>
                               <li class="nav-item dropdown">
                                  <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                     会議
                                  </a>
                                  <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                      <a class="dropdown-item" href="<c:url value='?action=${actMee}&command=${commNew}' />">会議を作成する</a>
                                      <a class="dropdown-item" href="<c:url value='?action=${actMee}&command=${commIdx}' />">会議一覧</a>
                                      <div class="dropdown-divider"></div>
                                      <a class="dropdown-item" href="<c:url value='?action=${actMee}&command=${commIdx}' />">アイデアを募集する</a>
                                 </div>
                               </li>
                               <li class="nav-item dropdown">
                                  <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                     プロジェクト
                                  </a>
                                  <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                      <a class="dropdown-item" href="<c:url value='?action=${actTask}&command=${commNew}' />">プロジェクトを作成する</a>
                                      <a class="dropdown-item" href="<c:url value='?action=${actTask}&command=${commIdx}' />">プロジェクト一覧</a>
                                 </div>
                               </li>
                               <li class="nav-item">
                                  <a class="nav-link" href="<c:url value='?action=${actTodo}&command=${commIdx}' />">TO DOリスト</a>
                               </li>
                               <li class="nav-item">
                                  <a class="nav-link" href="<c:url value='?action=${actAuth}&command=${commOut}' />">ログアウト</a>
                               </li>
                               <li class="nav-item dropdown">
                                  <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                      設定
                                  </a>
                                  <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                      <a class="dropdown-item" href="<c:url value='?action=${actEmp}&command=${commShow}&id=${sessionScope.login_employee.id}' />">プロフィール</a>
                                      <div class="dropdown-divider"></div>
                                      <c:if test="${sessionScope.login_employee.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}">
                                          <a class="dropdown-item" href="<c:url value='?action=${actTeam}&command=${commIdx}' />">チーム管理</a>
                                          <a class="dropdown-item" href="<c:url value='?action=${actEmp}&command=${commIdx}' />">従業員一覧</a>
                                      </c:if>
                                 </div>
                               </li>
                         </ul>
                 </div>
               </nav>
           </div>
        </div>


        <!-- ボディ -->
        <div id="content">
             ${param.content}
        </div>

        <!-- フッター -->
        <div id="footer">
        </div>
    </body>
</html>