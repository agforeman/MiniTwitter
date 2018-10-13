<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- TRY TO GET USER FROM SESSION OR COOKIE -->
<div id="header_bar">
    <c:if test="${user != null}">
            <form method="post" action="membership" id="signout_form">
                <input type="hidden" name="action" value="logout" />
                <input type="submit" name="logout" value="Sign Out" 
                       class="button logout_button"/>
            </form>
    </c:if>
</div>