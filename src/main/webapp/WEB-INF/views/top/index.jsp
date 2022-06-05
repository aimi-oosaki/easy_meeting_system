<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actAut" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="commSignUp" value="${ForwardConst.CMD_SIGNUP.getValue()}" />
<c:set var="commLogin" value="${ForwardConst.CMD_SHOW_LOGIN.getValue()}" />


<link rel"style" href="//maxcdn.boostrapcdn.com/font-awsome/4.3.0/css/font-awsome.min.css">
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>ジタン会議</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
    <div id="topPage_container">
        <div class="header_conteiner clearfix">
            <div class="header_left">
               <img class="logo" src="/meeting_system/img/logo.png">
            </div>
            <div class="header_right">
                <input class="input_btn" type="button" onclick="location.href='<c:url value='?action=${actAut}&command=${commLogin}' />'" value="ログイン">
            </div>
        </div>
        <div id="top_container">
            <h1>ジタン会議へようこそ</h1>
            <p>ジタン会議なら、会議に必要なさまざまなツールでよりスムーズな会議を行えます。</p>
            <div class="btn-signin"><input class="signin_btn" type="button" onclick="location.href='<c:url value='?action=${actEmp}&command=${commSignUp}' />'" value="新規登録"></div>
        </div>
        <div id="exp_container">
            <div id="exp_container-left">
                <div class="explain">
                    <h2>詳細な情報</h2>
                    <p>サイトやアプリのユーザー像を詳しく分析し、ご自身のマーケティングやコンテンツ、商品などのパフォーマンスを的確に把握できます。</p>
                </div>
                <div class="explain">
                    <h2>Google でしか得られないインサイト</h2>
                    <p>Google 独自のインサイトと機械学習機能によって、お客様のデータを最大限に活用できます。</p>
                </div>
                <div class="explain">
                    <h2>インサイトを成果に結び付ける</h2>
                    <p>アナリティクスは Google のさまざまな広告サービスやパブリッシャー サービスと連携できるため、インサイトを活用してビジネス上の成果につなげられます。</p>
                </div>
            </div>
            <div id="exp_container-right">
                <img src="/meeting_system/img/easy.png">
            </div>
        </div>
    </div>
    </body>
</html>