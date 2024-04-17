package com.college.student.file;

import com.college.student.pojo.ErrorResponse;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


@MultipartConfig(
)
public class FileServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FileServlet.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String directoryPath = "D:\\IdeaProjects-savedFiles\\";
        try {
            Part part = request.getPart("screenshot");
            String fileName = part.getSubmittedFileName();
            getServletContext().setAttribute("fileName", fileName);
            logger.info("Submitted File Name : {}", fileName + " Added to Context Level Attribute");
            part.write(directoryPath + fileName);
            logger.info("Saved File name to Disk");
            response.sendRedirect("DownloadFile.html");
        } catch (ServletException | IOException e) {
            logger.info("Exception Occurred While Uploading File: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(401, e.getMessage());
            response.setContentType("application/json");
            response.getWriter().println(new Gson().toJson(errorResponse));
            response.getWriter().flush();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String fileName = (String) getServletContext().getAttribute("fileName");
            logger.info("File Name Retrieved From Context : {}", fileName);
            File file = new File("D:\\IdeaProjects-savedFiles\\" + fileName);
            response.setContentType("application/octet-stream");
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[1000000000];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            logger.info("File Written to Output Stream for Download Successfully");
            in.close();
            out.flush();
        } catch (IOException e) {
            logger.info("Exception Occurred While Downloading File: {}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(401, e.getMessage());
            response.setContentType("application/json");
            response.getWriter().println(new Gson().toJson(errorResponse));
            response.getWriter().flush();
        }
    }
}
