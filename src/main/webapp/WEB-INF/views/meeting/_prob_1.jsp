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
        <c:import url="_timer.jsp" /><br />
        <div class="question-contaniner">
	        <div class="hidden_box">
			    <label for="label1">クリックして表示</label>
			    <input type="checkbox" id="label1"/>
			    <div class="hidden_show">
				    <!--非表示ここから-->     
				    <p><img src="face.png"></p>
				    <!--ここまで-->
			    </div>
			</div>
            <p>今抱えている問題は？</p>
        </div>
        <br />
        <div class="meeting-agenda">
            <p>議題を表示する<br>
            <c:forEach var="agenda" items="${agendas}">
                <input type="button" name="show-age" value="agenda">${agenda.title}
            </c:forEach>
            </p>
        </div>
        <br />
        <input class= "mee-btn-left"type="button" onclick="location.href='<c:url value='?action=${actMee}&command=${commIndex}' />'" value="一覧へ戻る">
        <input class= "mee-btn-right" type="button" onclick="location.href='<c:url value='?action=${actMee}&command=${commProb2}&meeting_id=${meeting.id}' />'" value="次へ">
    </div>
    </c:param>
</c:import>

<script>
function formSwitch() {
    hoge = document.getElementsByName('maker')
    if (hoge[0].checked) {
        // 好きな食べ物が選択されたら下記を実行します
        document.getElementById('foodList').style.display = "";
        document.getElementById('placeList').style.display = "none";
    } else if (hoge[1].checked) {
        // 好きな場所が選択されたら下記を実行します
        document.getElementById('foodList').style.display = "none";
        document.getElementById('placeList').style.display = "";
    } else {
        document.getElementById('foodList').style.display = "none";
        document.getElementById('placeList').style.display = "none";
    }
}
window.addEventListener('load', formSwitch());
</script>
