<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- TRY TO GET USER FROM SESSION OR COOKIE -->
<div id="header_bar">
    <c:if test="${user != null}">
        <ul>
            <li><a href="home.jsp">Home</a></li>
            <li><a href="">Notifications</a></li>
            <li><a href="signup.jsp">Profile</a></li>
            <!--<li style="float:right">-->
            <li id="sign-out-list-item">
                <form method="post" action="membership">
                    <input type="hidden" name="action" value="logout" />
                    <input type="submit" name="logout" value="Sign Out" 
                        class="logout_button"/></li>
                </form>
        </ul>
    </c:if>
</div>