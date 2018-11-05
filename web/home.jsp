<%-- 
    Document   : home.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
    Author     : xl
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
        <title>Home Page</title>
    </head>
    <body>
        <div id="header"><c:import url="header.jsp" /></div>
        <!-- TODO: BEFORE TESTING IF USER IS NULL CHECK COOKIE TO AND LOAD USER IF THERE IS A COOKIE -->
        <c:if test="${user == null}">
            <c:redirect url = "/login.jsp"/>
        </c:if>
        <c:if test="${users == null}">
            <c:redirect url = "/membership?action=get_users"/>
        </c:if>
        <c:if test="${tweets == null}">
            <c:redirect url = "/tweet?action=get_tweets"/>
        </c:if>
        <div class="flex-container">
            <div id="left_bar" class="side_column">
                <div id="user_info">
                    <img src="user_pic.jpg" alt="Profile Pic"/>
                    <p><c:out value='${user.fullname}'/></p>
                    <p><c:out value='@${user.username}'/></p>
                    <p>Number of Tweets</p>
                </div>
                <div id="trends">TRENDS</div>
            </div>
            <div id="middle_bar" class="main_column">
                <div id="tweet_composer">
                    <textarea name="user_tweet" id="composer" maxlength="280"
                        cols="40" rows="6" form="tweet_form" placeholder="Compose a New Tweet!"
                        required></textarea><br />
                    <form action="tweet" method="post" id="tweet_form">
                        <input type="hidden" name="action" value="post_tweet"/>
                        <input type="submit" value="Post Tweet" 
                               class="button tweet_button"/><br />
                    </form>                    
                </div>
                <div id="user_feed">
                    <c:forEach items="${tweets}" var="tweet_info">
                        <div class="tweets">
                            <img src="user_pic.jpg" alt="Profile Pic"/>
                            <span><c:out value='${tweet_info.fullname}'/></span>
                            <br />
                            <span><c:out value='@${tweet_info.username}:'/></span>
                            <span><c:out value='${tweet_info.date}'/></span>
                            <br />
                            <span>
                                <c:out value='${tweet_info.message}'/>
                            </span>
                            <div>
                                <form action="tweet" method="post">
                                    <input type="hidden" name="action" 
                                           value="delete_tweet"/>
                                    <input type="hidden" name="tweetID"
                                           value="${tweet_info.tweetid}"/>
                                    <input type="submit" value="Delete Tweet"
                                           class="button delete_button">
                                </form>
                            </div>
                        </div>
                        <br />
                    </c:forEach>    
                </div>
            </div>
            <div id="right_bar" class="side_column">
                <div id="suggested_users">
                    <h2>Who to follow?</h2>
                    <c:forEach items="${users}" var="suggested_user">
                        <div class="users">
                            <img src="user_pic.jpg" alt="Profile Pic"/>
                            <span><c:out value='${suggested_user.fullname}'/></span>
                            <br/>
                            <span><c:out value='@${suggested_user.username}'/></span>
                            <br />
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div id="footer"><c:import url="footer.jsp" /></div>
    </body>
</html>
