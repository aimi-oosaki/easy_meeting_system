<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actMin" value="${ForwardConst.ACT_MIN.getValue()}" />
<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="actWan" value="${ForwardConst.ACT_WAN.getValue()}" />
<c:set var="actTas" value="${ForwardConst.ACT_TAS.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commIndex" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commProb1" value="${ForwardConst.CMD_PROB1.getValue()}" />
<c:set var="commProb2" value="${ForwardConst.CMD_PROB2.getValue()}" />

<!-- HTML -->
<c:import url="../layout/app.jsp">
    <c:param name="content">
    <div id="content-wrapper">
        <!-- タイマー -->
        <c:import url="_timer.jsp" />
        <br />

        <!-- 質問 -->
        <div id="progress-container">
            <div class="center"><h4>進捗状況: 赤・黄のみ問題と対策を報告</h4></div><br />
            <table class="table">
                <tbody>
                    <tr>
                        <th class="text-center">プロジェクト名</th>
                        <th class="text-center">担当</th>
                        <th class="text-center">期日</th>
                        <th class="text-center">進捗状況</th>
                        <th class="text-center">課題</th>
                        <th class="text-center">対策</th>
                        <th class="text-center"></th>
                    </tr>
                    <c:forEach var="task" items="${tasks}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td><c:out value="${task.title}" /></td>
                            <td><c:out value="${task.employee.name}" /></td>
                            <td><c:out value="${task.due_date}" /></td>
                            <c:if test="${task.progress == AttributeConst.TAS_PROGRESS_DANGER.getIntegerValue()}">
                                <td id="status-danger"></td>
                            </c:if>
                            <c:if test="${task.progress == AttributeConst.TAS_PROGRESS_SAFE.getIntegerValue()}">
                                <td id="status-safe"></td>
                            </c:if>
                            <c:if test="${task.progress == AttributeConst.TAS_PROGRESS_CAUTIOUS.getIntegerValue()}">
                                <td id="status-cautious"></td>
                            </c:if>
                            <td><c:out value="${task.problem}" /></td>
                            <td><c:out value="${task.solution}" /></td>
                            <td><a href="<c:url value='?action=${actTas}&command=${commEdit}&task_id=${task.id}' />" >編集</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <br /><br />
    </div>
    </c:param>
</c:import>

<!-- Java Script -->
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
