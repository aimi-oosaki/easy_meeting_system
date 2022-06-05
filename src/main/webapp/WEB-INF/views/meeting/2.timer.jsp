<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!-- タイマー -->
<div class="time-container">
    <div class="timer-input-container">
        <input type="text" placeholder="00" class="timer" autofocus="true" id="min"/>
        <span>:</span>
        <input type="text" placeholder="00" class="timer" id="sec" />
    </div>
    <div class="timer-btn-container">
        <input type="button" value="▶" id="start" />
        <input type="button" value="■" disabled="" id="stop" />
        <input type="button" value="↺" disabled="" id="reset" />
    </div>
</div>


<!-- JavaScript -->
<script>



window.addEventListener("DOMContentLoaded", () => {
    //変数宣言
    const setMin = document.getElementById("min"); //画面の分
    const setSec = document.getElementById("sec"); //画面の秒
    const startBtn = document.getElementById("start"); //画面のstartボタン
    const stopBtn = document.getElementById("stop"); //画面のstopボタン
    const resetBtn = document.getElementById("reset"); //画面のresetボタン

    /*
     * Startボタンを押した時の処理
     */
    startBtn.addEventListener("click", () => {
        //Startボタンを押した時の時間を取得
        var time = new Date();
        let startTime = time.getTime();

        //画面に入力した分と秒を合計
        let inputMin = setMin.value * 60 * 1000; //分をミリ秒に換算
        let inputSec = setSec.value * 1000; //秒をミリ秒に換算

        if (inputMin == "") {
            inputMin = 0;
        }
        if (inputSec == "") {
            inputSec = 0;
        }
        let totalTime = parseInt(inputMin) + parseInt(inputSec);  //合計時間

        var testTimer;
        function countdown(){
            testTimer = setInterval(function(){
                totalTime -= 1

                //画面に表示する時間を計算
                const now = new Date(); //現在時刻を取得
                const diff = totalTime - now.getTime() - startTime; //時間の差を取得（ミリ秒）

                //ミリ秒から単位を修正
                const calcMin = Math.floor(diff / 1000 / 60) ;
                const calcSec = Math.floor(diff / 1000 % 60);

                //取得した時間を表示（2桁表示）
                if (calcMin < 0 && calcSec < 0) {
                    alert("時間です！")
                    setMin.value = ""
                    setSec.value = ""
                    clearInterval(testTimer); // タイマー停止
                } else {
                    min.innerHTML = calcMin < 10 ? '0' + calcMin : calcMin;
                    sec.innerHTML = calcSec < 10 ? '0' + calcSec : calcSec;
                }
            } , 1000);
       }
    });

//         const countdown = () => {
//             totalTime -= 1

//             //画面に表示する時間を計算
//             const now = new Date(); //現在時刻を取得
//             const diff = totalTime - now.getTime() - startTime; //時間の差を取得（ミリ秒）

//             //ミリ秒から単位を修正
//             const calcMin = Math.floor(diff / 1000 / 60) ;
//             const calcSec = Math.floor(diff / 1000 % 60);

//             //取得した時間を表示（2桁表示）
//             if (calcMin < 0 && calcSec < 0) {
//                 alert("時間です！")
//                 setMin.value = ""
//                 setSec.value = ""
//                 clearInterval(interval); // タイマー停止
//             } else {
//                 min.innerHTML = calcMin < 10 ? '0' + calcMin : calcMin;
//                 sec.innerHTML = calcSec < 10 ? '0' + calcSec : calcSec;
//             }
//         }, 1000);
//         countdown();
//         const interval = setInterval(countdown,1000);
//     });

    /*
     * Stopボタンを押した時の処理
     */
        stopBtn.addEventListener("click", () => {
        clearInterval(testTimer);
     });

    /*
     * Resetボタンを押した時の処理
     */
     resetBtn.addEventListener("click", () => {
         setMin.value = "";
         setSec.value = "";
    });


});
</script>