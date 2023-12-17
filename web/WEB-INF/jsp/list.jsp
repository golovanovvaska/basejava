<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.util.HtmlConverter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/list-styles.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">
    <div class="table-wrapper">
        <div class="add-resume">
            <a class="no-underline-anchor" href="resume?action=add">
                <img src="img/add_person.png" alt="add person">
            </a>
            <a class="text-anchor" href="resume?action=add">
                <p class="add-resume-title">Добавить резюме</p>
            </a>
        </div>
        <div class="resumes-list">
            <table>
                <tbody>
                <tr class="t-header">
                    <th class="name-column">Имя</th>
                    <th class="info-column">Контакты</th>
                    <th class="img-column">Удалить</th>
                    <th class="img-column">Редактировать</th>
                </tr>

                <c:forEach items="${resumes}" var="resume">
                    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
                    <tr class="t-body">
                        <td class="name-column">
                            <a class="contact-link" href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                        </td>
                        <td class="info-column">
                            <a class="contact-link"
                               href="mailto:${resume.getContact(ContactType.MAIL)}">${resume.getContact(ContactType.MAIL)}</a>
                        </td>
                        <td class="img-column">
                            <a class="contact-link" href="resume?uuid=${resume.uuid}&action=delete"><img class="delete"
                                                                                                         src="img/delete.png"></a>
                        </td>
                        <td class="img-column">
                            <a class="contact-link" href="resume?uuid=${resume.uuid}&action=edit"><img class="pencil"
                                                                                                       src="img/edit.png"></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>

</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>