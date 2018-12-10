<%-- 
    Document   : notifications
    Created on : Nov 3, 2018, 8:05:26 PM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
        <link rel="stylesheet" type="text/css" href="styles/notifications.css" />
        <link rel="stylesheet" type="text/css" href="styles/home.css" />
        <link rel="stylesheet" type="text/css" href="styles/header.css" />
        <link rel="stylesheet" type="text/css" href="styles/footer.css" />
        <title>Notifications Page</title>
    </head>
    <body>
        <c:import url="header.jsp" />
        <h1>New Followers!</h1>
        <div id="new_followers">
            <c:forEach items="${newFollowers}" var="follower">
                <p><c:out value='${follower}'/> is now following you!</p>
            </c:forEach>    
        </div>
        <br />
        <h1>New Tweets!</h1>
        <div id="new_tweets">
            <c:forEach items="${newTweets}" var="tweet_info">
                    <div class="tweets">
                        <div id="tweet_pic">
                        <img src="${pageContext.request.contextPath}/tweet?action=get_image&email=${tweet_info.emailAddress}" 
                                alt="Profile Pic"/>
                        </div>
                        <span><c:out value='${tweet_info.fullname}'/></span>
                        <br />
                        <span><c:out value='@${tweet_info.username}:'/></span>
                        <span><c:out value='${tweet_info.date}'/></span>
                        <br /><br />
                        <span>
                            <c:out value='${tweet_info.message}' escapeXml="false"/>
                        </span>
                        <br /><br />
                        <div>
                            <c:if test="${user.email.equals(tweet_info.emailAddress)}">
                            <form action="tweet" method="post">
                                <input type="hidden" name="action" 
                                       value="delete_tweet"/>
                                <input type="hidden" name="tweetID"
                                       value="${tweet_info.tweetid}"/>
                                <input type="submit" value="Delete Tweet"
                                       class="button delete_button">
                            </form>
                            </c:if>  
                        </div>
                    </div>
                </c:forEach> 
        </div>
        <c:import url="footer.jsp" />
    </body>
</html>
