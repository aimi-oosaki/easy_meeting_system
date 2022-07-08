<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commIndex" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commProb1" value="${ForwardConst.CMD_PROB1.getValue()}" />
<c:set var="commProb2" value="${ForwardConst.CMD_PROB2.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
    <div id="content-wrapper">
        <!-- タイマー -->
        <c:import url="_timer.jsp" /><br />

        <!-- 質問 -->
        <h1 id="idName"></h1>
        <div class="question-contaniner">
            <p>今抱えている問題は？</p>
        </div>
        <br />
        <br />
        <input class= "mee-btn-left"type="button" onclick="location.href='<c:url value='?action=${actMee}&command=${commIndex}' />'" value="一覧へ戻る">
        <input class= "mee-btn-right" type="button" onclick="location.href='<c:url value='?action=${actMee}&command=${commProb2}&meeting_id=${meeting.id}' />'" value="次へ">
    </div>
    </c:param>
</c:import>


<script>
var myfunc = function(){
    var myh1 = document.getElementById("idName");
    myh1.innerHTML = "クリック後";
}

function formSwitch() {
    var fruits = document.getElementsByName("age");
    for(var i = 0; i < fruits.length; i++){
      if(fruits[i].checked) {
          console.log("選択された値：", fruits[i].value);
//           document.getElementById('agenda').style.display = "";
          document.getElementById("agenda").innerHTML = fruits[i].value;
      }
    }
}

</script>
