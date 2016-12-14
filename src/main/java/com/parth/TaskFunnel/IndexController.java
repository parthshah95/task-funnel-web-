package com.parth.TaskFunnel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*** Created by parshah on 17-Sep-16.*/

@Controller
public class IndexController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexController() {

        return "/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json", produces = "plain/text")
    public ResponseEntity<String> saveTask(@RequestBody String taskJson) {
        int startIndex = taskJson.indexOf("\"id\":\"") + 6;
        int endIndex = taskJson.indexOf("\"", startIndex);
        String taskId = taskJson.substring(startIndex, endIndex);
        /*System.out.print(startIndex + "\t");
        System.out.print(endIndex + "\t");
        System.out.println(taskId);*/
        String msg = "";
        File dir = new File("save-dir");
        if (!dir.exists()) dir.mkdir();
        File jsonFile = new File("save-dir/tasks/" + taskId + ".json");
        if (!jsonFile.exists()) {
            try {
                jsonFile.createNewFile();
            } catch (java.io.IOException e) {
                System.out.println(e);
                msg = e.toString();
            }
        }
        try {
            FileWriter fw = new FileWriter(jsonFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(taskJson);
            bw.close();
            msg = "saved :)";
        } catch (java.io.IOException e) {
            System.out.println(e);
            msg = e.toString();
        }


        return new ResponseEntity<String>(msg, HttpStatus.ACCEPTED);
    }
}
