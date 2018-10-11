<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="date" scope="session" value="<%= new java.util.Date() %>"/>
<span>&copy; Copyright 2018 Alex Foreman &amp; Paul Brown</span>
<span><fmt:formatDate dateStyle="FULL" value="${sessionScope.date}"/></span>