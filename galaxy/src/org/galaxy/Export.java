package org.galaxy;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.refine.ProjectManager;
import com.google.refine.browsing.Engine;
import com.google.refine.commands.Command;
import com.google.refine.exporters.CsvExporter;
import com.google.refine.model.Project;


public class Export extends Command {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String project_string = request.getParameter("project");
        if (project_string != null) {
            System.err.println("Parse:" + project_string);
            long project_id = Long.parseLong(project_string);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/json");

            Project project = ProjectManager.singleton.getProject( project_id );

            Writer writer = response.getWriter();

            CsvExporter cxp = new CsvExporter('\t');
            Engine engine = new Engine(project);
            cxp.export(project, null, engine, writer);
        }
    }

}
