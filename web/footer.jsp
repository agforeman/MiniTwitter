<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="footer_bar" >
    <c:set var="date" scope="session" value="<%= new java.util.Date() %>"/>
    <div class="info">
        <span class="info copyright_info">&copy; Copyright 2018 Alex Foreman &amp; Paul Brown</span>
        <span class="info date_info"><fmt:formatDate dateStyle="FULL" value="${sessionScope.date}"/></span>
    </div>
</div>