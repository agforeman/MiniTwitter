<%-- 
    Document   : hashtag
    Created on : Dec 11, 2018, 2:02:27 PM
    Author     : Paul Brown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
        <link rel="stylesheet" type="text/css" href="styles/home.css" />
        <link rel="stylesheet" type="text/css" href="styles/header.css" />
        <link rel="stylesheet" type="text/css" href="styles/footer.css" />
        <title>HashTag Info</title>
    </head>
    <body>
        <div id="header"><c:import url="header.jsp" /></div>
        <c:if test="${user == null}">
            <c:redirect url = "/login.jsp"/>
        </c:if>
        <H2>Hashtag in Tweets</H2>
        <c:forEach items="${hashTagTweets}" var="tweet_info">
            <div class="tweets">
                <div id="tweet_pic">
                    <img src="${pageContext.request.contextPath}/tweet?action=get_image&email=${tweet_info.emailAddress}" 
                            alt="Profile Pic"/>
                </div>
                    <span><c:out value='${tweet_info.fullname}'/></span><br />
                    <span><c:out value='@${tweet_info.username}:'/></span>
                    <span><c:out value='${tweet_info.date}'/></span><br /><br />
                    <span><c:out value='${tweet_info.message}' escapeXml="false"/></span><br /><br />
            </div>
        </c:forEach>
        <div id="footer"><c:import url="footer.jsp" /></div>
    </body>
</html>
