package org.galaxy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import com.google.refine.ProjectManager;
import com.google.refine.ProjectMetadata;
import com.google.refine.importers.SeparatorBasedImporter;
import com.google.refine.importing.ImportingJob;
import com.google.refine.importing.ImportingManager;
import com.google.refine.importing.ImportingUtilities;
import com.google.refine.model.Project;


public class GalaxyInterface {

    public static long project_import(String sourceUrl) {
        try {
            
            URL url = new URL(sourceUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(5000);

            urlConnection.connect();

            InputStream stream2 = urlConnection.getInputStream();

            ImportingJob job = ImportingManager.createJob();

            File file = ImportingUtilities.allocateFile(job.getRawDataDir(), "test_galaxy");
            ImportingUtilities.saveStreamToFile(stream2, file, null);
            stream2.close();
            
            SeparatorBasedImporter sepImp = new SeparatorBasedImporter();
           
            
            ProjectMetadata pm = new ProjectMetadata();
            pm.setName("Untitled");
            pm.setEncoding("UTF-8");
            
            List<Exception> exceptions = new LinkedList<Exception>();
            Project project = new Project();
            JSONObject options = new JSONObject();
            FileReader reader = new FileReader(file);
            sepImp.parseOneFile(project, pm, job, file.getAbsolutePath(), reader, 0, options, exceptions);
            project.update();
            ProjectManager.singleton.registerProject(project, pm);
            ProjectManager.singleton.save(true);            
            return project.id;
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      
        return 15;
    }
    
}