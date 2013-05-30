<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/chat.js"></script>
<script type="text/javascript">
    var send = function() {
        var msg = document.getElementById('message').value;
        if (msg) {
            chat.send(msg);
            document.getElementById('message').value = '';
        }
    };
</script>
<link rel="stylesheet" type="text/css" href="resources/style.css">
</head>
<body onload="chat.init();">
    <div class="chat-body">
        <select multiple="multiple" id="messages" class="messages"></select>
        <div class="chat-send">
            <div class="message-error"></div>
            <input class="message" id="message" type="text"
                onkeyup="if (event.keyCode === 13) {send();};"></input>
            <div class="button-panels">
                <ul>
                    <li><div class="button" id="send">
                            <a href="#" onclick="send();">Send</a>
                        </div></li>
                    <li><div class="button" id="logout">
                            <a href="login.jsp">Logout</a>
                        </div></li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>