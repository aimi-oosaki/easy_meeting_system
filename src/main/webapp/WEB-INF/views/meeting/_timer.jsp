<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!-- タイマー -->
<div class="timer-cotainer">
  <div id="timer">00:00</div>
  <div class="controls">
    <button id="min">Min</button>
    <button id="sec">Sec</button>
    <button id="reset">Reset</button>
    <button id="start">Start</button>
  </div>
</div>
<!-- <div class="timer-cotainer"> -->
<!--   <div id="timer">00:00.000</div> -->
<!--   <div class="controls"> -->
<!--     <button id="min">Min</button> -->
<!--     <button id="sec">Sec</button> -->
<!--     <button id="reset">Reset</button> -->
<!--     <button id="start">Start</button> -->
<!--   </div> -->
<!-- </div> -->

<!-- JavaScript -->
<script>
(function() {
      'use strict';

      var timer = document.getElementById('timer');  //タイマー表示部分
      var min = document.getElementById('min');  //分ボタン
      var sec = document.getElementById('sec');  //秒ボタン
      var reset = document.getElementById('reset');  //リセットボタン
      var start = document.getElementById('start');  //スタートボタン

      var startTime;
      var timeLeft;
      var timeToCountDown = 0;
      var timerId;
      var isRunning = false;

      /*
      ミリ秒を渡すと分・秒に直してくれる関数
      */
      function updateTimer(t) {
        var d = new Date(t); //渡されたtでDateオブジェクトを作成
        var m = d.getMinutes(); //分を取り出す
        var s = d.getSeconds(); //秒を取り出す
        var ms = d.getMilliseconds(); //ミリ秒を取り出す
        m = ('0' + m).slice(-2);  //末尾2桁を取り出す
        s = ('0' + s).slice(-2);
        ms = ('00' + ms).slice(-3);
//         timer.textContent = m + ':' + s + '.' + ms;
        timer.textContent = m + ':' + s;
      }

      /*
      ミリ秒を渡すと分・秒に直してくれる関数
      */
      function countDown() {
          timerId = setTimeout(function() {
          // var elapsedTime = Date.now() - startTime;
          // timeLeft = timeToCountDown - elapsedTime;
          timeLeft = timeToCountDown - (Date.now() - startTime);
          // console.log(timeLeft);
          if (timeLeft < 0) {
            isRunning = false;
            start.textContent = 'Start';
            clearTimeout(timerId);
            clearTimeout(timerId);
            timeLeft = 0;
            timeToCountDown = 0;
            updateTimer(timeLeft);
            return;
          }
          updateTimer(timeLeft);
          countDown();
        }, 10);
      }

      //Startクリックした時のイベント
      start.addEventListener('click', function() {
           if (isRunning === false) {
               isRunning = true;
               start.textContent = 'Stop';
               startTime = Date.now();
               countDown();
           } else {
               isRunning = false;
               start.textContent = 'Start';
               timeToCountDown = timeLeft;
               clearTimeout(timerId);
           }
      });

      //分をクリックした時のイベント
      min.addEventListener('click', function() {
             if (isRunning === true) {
               return;
             }
             timeToCountDown += 60 * 1000;
             if (timeToCountDown >= 60 * 60 * 1000) {
               //60min以上になったら
               timeToCountDown = 0;
             }
             updateTimer(timeToCountDown);
      });

      //秒をクリックした時のイベント
      sec.addEventListener('click', function() {
            if (isRunning === true) {
                return;
            }
            timeToCountDown += 1000;
            if (timeToCountDown >= 60 * 60 * 1000) {
                //60min以上になったら
                timeToCountDown = 0;
            }
            updateTimer(timeToCountDown);
      });

      //リセットをクリックした時のイベント
      reset.addEventListener('click', function() {
          timeToCountDown = 0;
          updateTimer(timeToCountDown);
      });
})();
</script>