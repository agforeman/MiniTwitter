
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
        <title>Login</title>
    </head>
    <body>
        <c:import url="header.jsp" />
        <h1>Login</h1>
        <form method="post" action="membership">
            <input type="hidden" name="action" value="login" />
            <label class="pad_top">Email:</label>
            <input type="email" name="email" value="${user.email}" placeholder="Email or Username" ><br>
            <label class="pad_top">Password:</label>
            <input type="password" name="password" value="${user.password}" placeholder="Enter Password" ><br>
            <input type="submit" value="Login"> 
            <input type="checkbox" name="remember" value="Remember Me"><span>Remember Me</span>
            <a href="forgotpassword.jsp">Forgot Password?</a><br><br>
           
            <p>New? <a href="signup.jsp">Sign Up Now</a></p>
            <script type="text/javascript">
                response.redirect("/membership");
                </script>
        </form>    
        <c:import url="footer.jsp" />
    </body>
</html>
