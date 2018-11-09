<!DOCTYPE html>
<html>
<head>
    <title>Sign-Up Page</title>
    <meta charset="utf-8">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link rel="stylesheet" type="text/css" href="styles/main.css" />
    <link rel="stylesheet" type="text/css" href="styles/signup.css" />
    <link rel="stylesheet" type="text/css" href="styles/header.css" />
    <link rel="stylesheet" type="text/css" href="styles/footer.css" />
    <script src="includes/main.js" type="text/javascript"></script>
</head>
<body>
    <c:import url="header.jsp" />
    <c:if test="${not empty signupError}">
        <p><c:out value="${signupError}"/></p>
    </c:if>
    <h1>Sign Up Form</h1>
    <div id="error_message" class="notVisible"></div>
    <form action="membership" method="post" onsubmit="return validateForm();"
          id="signup_form" enctype="multipart/form-data">
         <c:choose>
            <c:when test="${user != null}">
                <input type="hidden" name="action" value="update"> 
            </c:when>
            <c:otherwise>
                <input type="hidden" name="action" value="signup">
            </c:otherwise>
        </c:choose>      
        <label class="pad_top">Fullname:</label>
        <input type="text" id="fullname" name="fullname" placeholder="Full Name" 
               value="<c:out value='${user.fullname}'/>" required />
        <span id="fullname_error" class="notVisible">*</span><br />
        
        <label class="pad_top">Username:</label>
        <c:choose>
            <c:when test="${user != null}">
                <input type="text" style="background-color:lightgray;" 
                    value="<c:out value='${user.username}'/>" readonly />
            </c:when>
            <c:otherwise>
                <input type="text" id="username" name="username" placeholder="Username"
                   required />
            </c:otherwise>
        </c:choose>      
        <span id="username_error" class="notVisible">*</span><br />
       
        <label class="pad_top">Email:</label>
         <c:choose>
            <c:when test="${user != null}">
                <input type="text" style="background-color:lightgray;" 
                    value="<c:out value='${user.email}'/>" readonly />
            </c:when>
            <c:otherwise>
                <input type="text" id="email" name="email" placeholder="email"
                   required />
            </c:otherwise>
        </c:choose>
        <span id="email_error" class="notVisible">*</span><br />
        
        <label class="pad_top">Password:</label>
        <input type="password" id="password" name="password" placeholder="Password"
               required />
        <span id="password_error" class="notVisible">*</span><br />
        
        <label class="pad_top">Confirm Password:</label>
        <input type="password" id="confirm_password" name="confirm_password"
               placeholder="Confirm Password" required />
        <span id="confirm_error" class="notVisible">*</span><br />
        
        <label class="pad_top">Date of Birth:</label>
        <input type="date" id="dateofbirth" name="birthdate" 
               value="<c:out value='${user.birthdate}'/>" required />
        <span id="dateofbirth_error" class="notVisible">*</span><br />
            
        <label class="pad_top">Question:</label>
        <select name="security_questions" id="security_questions" 
                form="signup_form" required onchange="DynamicForm()">
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
        <span id="security_answer_error" class="notVisible">*</span><br /><br />
        
        <label class="pad_top">Upload Picture:</label>
        <input type="file" id=photo name="photo" />
        <span id="upload_error" class="notVisible">*</span><br />
        
        <!-- Form buttons-->
        <c:choose>
            <c:when test="${user != null}">
                <input type="submit" value="Update" class="button submit_button" />
            </c:when>
            <c:otherwise>
                <input type="submit" value="Sign Up" class="button submit_button" />
                <input type="reset" value="Clear" onclick="cleanup()" class="button reset_button" />
            </c:otherwise>
        </c:choose>      
        
    </form>
    <c:import url="footer.jsp" />
</body>