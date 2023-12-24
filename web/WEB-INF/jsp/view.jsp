<%@ page import="com.basejava.webapp.util.HtmlConverter" %>
<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.OrganizationSection" %>
<%@ page import="com.basejava.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/view-styles.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">
    <div class="form-wrapper">
        <h2 class="full-name">${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">
            <img class="pencil" src="img/edit.png"></a></h2>
        <c:if test="<%=!resume.getContacts().isEmpty()%>">
        <ul class="contacts">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.basejava.webapp.model.ContactType , java.lang.String>"/>
                <%=HtmlConverter.contactsToHtml(contactEntry.getKey(), contactEntry.getValue())%>
            </c:forEach>
        </ul>
        </c:if>
        <div class="spacer"></div>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.basejava.webapp.model.Sections
                                            , com.basejava.webapp.model.Section>"/>
            <c:if test="<%=sectionEntry.getValue() != null%>">
                <div class="section">
                    <h3 class="section">${sectionEntry.key.section}</h3>
                    <%=HtmlConverter.sectionsToView(sectionEntry.getKey(), sectionEntry.getValue())%>
                </div>
            </c:if>
        </c:forEach>
    </div>

</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>