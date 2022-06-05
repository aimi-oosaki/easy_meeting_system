<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actMee" value="${ForwardConst.ACT_MEE.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />


<!-- タイマー -->
<div class="time-view">
  <div class="free-block">
    <input type="text" placeholder="00" class="free-timer" autofocus="true" id="free-min"/>
    <span class="free">:</span>
    <input type="text" placeholder="00" class="free-timer" id="free-sec" />
  </div>
<!--   <div class="min-sec"> -->
<!--     <button value="10" id="min-10" class="add-btn">10分</button> -->
<!--     <button value="5" id="min-5" class="add-btn">5分</button> -->
<!--     <button value="1" id="min-1" class="add-btn">1分</button> -->
<!--   </div> -->
  <div class="free-box">
    <input type="button" value="▶" id="free-start" />
    <input type="button" value="■" disabled="" id="free-stop" />
    <input type="button" value="↺" disabled="" id="free-reset" />
  </div>
</div>

<!-- JavaScript -->
<script>
window.addEventListener("DOMContentLoaded", () => {
      const inputMin = document.getElementById("free-min"); //画面の分
      const inputSec = document.getElementById("free-sec"); //画面の秒
      const freeStart = document.getElementById("free-start"); //画面のstartボタン
      const freeStop = document.getElementById("free-stop"); //画面のstopボタン
      const freeReset = document.getElementById("free-reset"); //画面のresetボタン


      // startボタンを押した際の処理
      freeStart.addEventListener("click", () => {

          //画面に入力した分と秒を合計
          let countMin = inputMin.value * 60;
          let countSec = inputSec.value;
          if (countMin == "") {
            countMin = 0;
          }
          if (countSec == "") {
            countSec = 0;
          }
          let mixValue = parseInt(countMin) + parseInt(countSec);


          const freeCount = () => {
              // stopボタンを押した際の処理
              freeStop.addEventListener("click", () => {
              clearInterval(freeInterval);
              })

               //入力した合計時間から、画面に表示する分と秒を計算
               mixValue -= 1
               freeMin = Math.floor(mixValue / 60); //分を計算（分と秒の合計時間/60sの小数点以下を切り捨て）
               freeSec = Math.floor(mixValue % 60); //秒を計算（分と秒の合計時間/60sの余り）

               if (freeMin < 10 ) {
                 //10分以下なら数字を0埋めする
                 inputMin.value = `0${freeMin}`
               } else {
                 inputMin.value = freeMin
               }

               if (freeSec < 10 ) {
                 inputSec.value = `0${freeSec}`
               } else {
                 inputSec.value = freeSec
               }

               if (mixValue < 0) {
                 alert("時間です！")
                 inputMin.value = ""
                 inputSec.value = ""
                 clearInterval(freeInterval);
               }
          }

          freeCount(mixValue);
          const freeInterval = setInterval(freeCount, 1000); //1s毎にfreeCountを呼び出す
      });

      // resetボタンを押した際の処理
      freeReset.addEventListener("click", () => {
          inputMin.value = "";
          inputSec.value = "";
      });
});
</script>
