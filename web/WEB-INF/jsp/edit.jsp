<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.util.HtmlConverter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/edit-styles.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <div class="scrollable-panel">
            <div class="form-wrapper">
                <div class="section">ФИО</div>
                <input type="text" name="fullName" size=50 placeholder="ФИО" value="${resume.fullName}" pattern="^[^\s][a-zA-Zа-яА-Я\s]*$" required>
                <div class="section">Контакты</div>
                <c:forEach var="type" items="<%=ContactType.values()%>">
                    <input class="field" type="text" name="${type.name()}" size=30 placeholder="${type.contact}"
                           value="${resume.getContact(type)}">
                </c:forEach>
                <div class="section">Секции</div>
                <c:forEach var="sectionEntry" items="${resume.sections}">
                    <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.basejava.webapp.model.Sections
                                            , com.basejava.webapp.model.Section>"/>
                    <c:if test="<%=sectionEntry.getValue() != null%>">
                        <div class="section">
                            <div class="section">${sectionEntry.key.section}</div>
                            <%=HtmlConverter.sectionsToEdit(sectionEntry.getKey(), sectionEntry.getValue())%>
                        </div>
                    </c:if>
                </c:forEach>
                <div class="button-section">
                    <button class="red-cancel-button" onclick="window.history.back()">Отменить</button>
                    <button class="green-submit-button" type="submit">Сохранить</button>
                </div>
            </div>
        </div>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>