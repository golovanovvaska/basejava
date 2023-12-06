package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            response.getWriter().write(drawTable(uuid, storage.get(uuid).getFullName()));
        } else {
            List<Resume> resumes = storage.getAllSorted();
            StringBuilder table = new StringBuilder();
            for (Resume resume : resumes) {
                table.append(drawTable(resume.getUuid(), resume.getFullName()));
            }
            response.getWriter().write(table.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private String drawTable(String uuid, String name) {
        return "<style>\n" +
                "table, th, td {\n" +
                "  border:1px solid black;\n" +
                "  text-align: left;\n" +
                "}\n" +
                "</style><table>\n" +
                "  <tr>\n" +
                "    <th>uuid</th>\n" +
                "    <th>full_name</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>" + uuid + "</td>\n" +
                "    <td>" + name + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }
}
