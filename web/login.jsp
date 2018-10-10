
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="header.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="post" action="login">
            <label class="pad_top">Email:</label>
            <input type="email" name="email" value="${user.email}" placeholder="Email or Username" ><br>
            <label class="pad_top">Password:</label>
            <input type="password" name="password" value="${user.password}" placeholder="Enter Password" ><br>
            <input type="hidden" name="action" value='login'>
            <input type="submit" value="Login"> 
            <a href="forgotpassword.jsp">Forgot Password?</a><br><br>
           
            <p>New? <a href="signup.jsp">Sign Up Now</a></p>
            <script type="text/javascript">
                response.redirect("/membership");
                </script>
        </form>    
    </body>
    <c:import url="footer.jsp" />
</html>
