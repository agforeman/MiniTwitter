
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
         <link rel="stylesheet" type="text/css" href="styles/login.css" />
        <link rel="stylesheet" type="text/css" href="styles/header.css" />
        <link rel="stylesheet" type="text/css" href="styles/footer.css" />

        <title>Login</title>
    </head>
    <body>
        <c:import url="header.jsp" />
        <!-- If the user is already signed in go directly to homepage -->
        <c:if test="${user != null}">
            <c:redirect url="/home.jsp"/>
        </c:if>
        <c:if test="${not empty loginError}">
            <p><c:out value="${loginError}"/></p>
        </c:if>
         <c:if test="${not empty forgotMessage}">
            <p><c:out value="${forgotMessage}"/></p>
        </c:if>
        
        
        <h1>Login</h1>
        <form method="post" action="membership">
            <input type="hidden" name="action" value="login" />
            <input type="email" name="email" value="<c:out value='${user.email}'/>" 
                   placeholder="Email" required ><br>
            <input type="password" name="password" value="<c:out value='${user.password}'/>" 
                   placeholder="Enter Password" required><br>
            <input type="submit" value="Login" class="button login_button"> 
            <p style="font-size:12px">
            <input type="checkbox" name="remember" value="remember"><span>Remember Me</span> 
            <a href="forgotpassword.jsp">Forgot Password?</a><br><br>
            New? <a href="signup.jsp">Sign Up Now</a></p>
        </form>    
        <c:import url="footer.jsp" />
    </body>
</html>
