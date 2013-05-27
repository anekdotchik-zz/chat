<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
    <form action="login" method="post" id="login">
        <table>
            <tr>
                <td><label for="user">User:</label></td>
                <td><input type="text" name="user" /></td>
            </tr>
            <tr>
                <td><label for="pass">Pass:</label></td>
                <td><input type="password" name="pass"
                    onkeyup="if (event.keyCode == 13) {document.getElementById('login').submit();}" /></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="login" name="login" />
                <td>
            </tr>
        </table>
    </form>
</body>
</html>