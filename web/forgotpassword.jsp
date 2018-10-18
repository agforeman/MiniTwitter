<%-- 
    Document   : forgotpassword
    Created on : Oct 10, 2018, 3:25:57 PM
    Author     : Paul Brown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="styles/main.css" />
    <link rel="stylesheet" type="text/css" href="styles/header.css" />
    <link rel="stylesheet" type="text/css" href="styles/footer.css" />
    <link rel="stylesheet" type="text/css" href="styles/signup.css" />
    <script src="includes/main.js" type="text/javascript"></script>
        
    <title>Forgot Password</title>
</head>
<body>
    <c:import url="header.jsp" />
    <c:if test="${not empty forgotMessage}">
        <p><c:out value="${forgotMessage}"/></p>
    </c:if>
    <h1>Forgot Password</h1>
    <form method="post" action="membership">
        <input type="hidden" name="action" value="forgot" />
        <label class="pad_top">Email:</label>
        <input type="email" name="email" value="<c:out value='${user.email}'/>" 
            placeholder="Email" required ><br>
        <label class="pad_top">Question:</label>
        <select name="security_questions" id="security_questions" 
                required onchange="DynamicForm()">
            <option disabled selected value>Select an option</option>
            <option value="0">
                What was the name of your first pet?
            </option>
            <option value="1">
                What model was your first car?
            </option>
            <option value="2">
                What was the name of your first school?
            </option>
        </select>
        <span id="security_question_error" class="notVisible">*</span><br />
    
        <label class="pad_top"></label>
        <input type="text" id="security_answer" name="security_answer" class="notVisible"
            placeholder="Answer" required />
        <span id="security_answer_error" class="notVisible">*</span><br />
                
        <input type="submit" value="Submit" class="button submit_button"> 
    </form>
    <c:import url="footer.jsp" />
</body>
</html>
