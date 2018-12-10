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
        <c:if test="${userFollows == null}">
            <c:redirect url = "/tweet?action=get_allfollows"/>
        </c:if>
        <div class="flex-container">
            <div id="left_bar" class="side_column">
                <div id="user_info">
                    <div id="profile_pic">
                        <img src="${pageContext.request.contextPath}/tweet?action=get_image&email=${user.email}" 
                             alt="Profile Pic"/>
                    </div>    
                    <p><c:out value='${user.fullname}'/></p>
                    <p><c:out value='@${user.username}'/></p>
                    <p><b><c:out value='${numberOfTweets}'/></b> Tweets</p>
                    <p><b><c:out value='${numberOfFollowers}'/></b> Follower(s)!</p>
                    <p>Following: <b><c:out value='${numberOfFollowing}'/></b> User(s)!</p>
                </div>
                <div id="trends">
                    <h2>TRENDS</h2>
                </div>
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
            </div>
            <div id="right_bar" class="side_column">
                
                    <h2>Who to follow?</h2>
                    <c:set var="done" value='${false}'/>
                    <c:forEach items="${users}" var="suggested_user">   
                        <c:if test='${user.email != suggested_user.email}'>
                            <div class="users">
                                <div id ="follow_pic" >
                                    <img src="/MiniTwitter/tweet?action=get_image&email=${suggested_user.email}"
                                            alt="Profile Pic"/>
                                </div>
                                <span><c:out value='${suggested_user.fullname}'/></span>
                                <br/>
                                <span><c:out value='@${suggested_user.username}'/></span>                                         
                                
                                <!-- Follow/Unfollow button form -->
                                <form action="tweet" method="post"/>
                                <c:choose> 
                                <c:when test='${empty userFollows}'>
                                    <!-- Display the follow buttons if there are no followed users -->
                                    <input type="hidden" name="action" value="follow_user"/>
                                    <input type="hidden" name="followedUserID" value="${suggested_user.id}"/>
                                    <input type="submit" value="Follow" class="button delete_button"> 
                                </c:when>
                                <c:otherwise>
                                <!-- Display the un-follow or the follow button if there are followed users-->
                                <c:forEach items="${userFollows}" var="user_follows">
                                    <!-- Processing each followed user in the follow list -->
                                    <c:if test="${suggested_user.id.equals(user_follows.followedUserID)}">
                                        <c:set var="done" value='${true}'/>
                                        <!-- If the followed user we have == the suggested user put unfollow only
                                             if you haven't already assigned a button -->
                                        <input type="hidden" name="action" value="unfollow_user"/>
                                        <input type="hidden" name="followedUserID" value="${user_follows.followedUserID}"/>
                                        <input type="submit" value="Unfollow" class="button delete_button">
                                    </c:if>                           
                                </c:forEach>
                                <!-- If the followed user is not the suggested user put follow -->
                                <c:if test='${done == false}'>
                                    <!-- Only add the follow button to this suggested user if you haven't
                                         done so already -->
                                    <input type="hidden" name="action" value="follow_user"/>
                                    <input type="hidden" name="followedUserID" value="${suggested_user.id}"/>
                                    <input type="submit" value="Follow" class="button delete_button">
                                </c:if>    
                                <c:set var="done" value='${false}'/>
                                </c:otherwise>
                                </c:choose>
                                </form>
                            </div>
                        </c:if>
                    </c:forEach>
                
            </div>
        </div>
        <div id="footer"><c:import url="footer.jsp" /></div>
    </body>
</html>
