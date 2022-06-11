<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../layout/app.jsp">
    <c:param name="content">
    <div id="content-wrapper">
        <!-- 付箋 -->
        <div class="main-container">
            <div class="container1">
                <button class="btn btn-warning" onclick="typeNote()">Create Note</button>
            </div>
            <div class="container2"></div>
            <div class="container3">
                <form>
                    <textarea id="note-text" placeholder="Write Note..." maxlength="96"></textarea>
                    <!-- チェックボタン -->
                    <svg id="check-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16">
                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                        <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                    </svg>
                    <!-- ×ボタン -->
                    <svg id="x-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
                       <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                       <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                    </svg>
                </form>
            </div>
        </div>
    </div>
    </c:param>
</c:import>

        <!-- JavaScript -->
        <script>
        var container2 = document.getElementsByClassName("container2")[0];
        var container3 = document.getElementsByClassName("container3")[0];
        var checkIcon = document.getElementById("check-icon");
        var xIcon = document.getElementById("x-icon");
        var i = 0;

        xIcon.addEventListener("click", function(){
            typeNote();
        })

        checkIcon.addEventListener("click", function(){
            createNote();
        })

        function typeNote(){
            if(container3.style.display == "none"){
                container3.style.display == "block";
            }
            else{
                container3.style.display == "none";
            }
        }

        function createNote(){
            var noteText = document.getElementById("note-text").value;
            var node0 = document.createElement("div");
            var node1 = document.createElement("h1");

            node1.innerHTML = noteText;

            node1.setAttribute("style", "width:250px; height:250px; font-size:26px; padding:25px; margin-top:10px; overflow:hidden; box-shadow:0px 10px 24px 0px rgba(0,0,0,0.75)");

            node1.style.margin = margin();
            node1.style.transform = rotate();
            node1.style.background = color();

            node0.appendChild(node1);

            container2.insertAdjacentElement("beforeend", node0);

            node0.addEventListener("mouseenter", function(){
                node0.style.transform = "scale(1.1)";
            })

            node0.addEventListener("mouseleave", function(){
                    node0.style.transform = "scale(1)";
            })

            node0.addEventListener("dblclick", function(){
                    node0.remove();
            })

            document.getElementById("note-text").value = '';
        }

        function margin(){
            var random_margin = ["-5px", "1px", "5px", "10px", "15px", "20px"];
            return random_margin[Math.floor(Math.random() * random_margin.length)];
        }

        function rotate(){
            var random_rotate = ["rotate(3deg)", "rotate(1deg)", "rotate(-1deg)", "rotate(-3deg)", "rotate(-5deg)", "rotate(-10deg)"];
            return random_rotate[Math.floor(Math.random() * random_rotate.length)];
        }

        function color(){
            var random_color = ["#c2ff3d", "#ff3de8", "#3dc2ff", "#04e022", "#bc83e6", "#ebb328"];

            if(i > random_color.length - 1){
                i = 0;
            }
            return random_color[i++];
        }
        </script>
