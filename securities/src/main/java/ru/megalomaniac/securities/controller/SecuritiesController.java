package ru.megalomaniac.securities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.megalomaniac.securities.xml.SecuritiesXmlImport;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping(path="/")
public class SecuritiesController {
    @Autowired
    SecuritiesXmlImport securitiesXmlImport;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("theDate",new java.util.Date());
        return "index";
    }

    @GetMapping("/import")
    public String importFromXml(Model model){
        String filename = "/home/megalom/tmp/history_1.xml";
        boolean isSuccessful=false;
        try(FileInputStream fis = new FileInputStream(filename)){
            isSuccessful=securitiesXmlImport.importFromFile(fis);
            if(isSuccessful){
                model.addAttribute("recordsCount",securitiesXmlImport.getRecordsCount());
                model.addAttribute("errors",securitiesXmlImport.getErrors());
                securitiesXmlImport.saveToDb();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("isSuccessful",isSuccessful);

        return "import/import-result";
    }

    @GetMapping("/upload")
    public String uploadForm(){

        /* REST запрос к бирже для получения информации о ценных бумагах
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "http://iss.moex.com/iss/securities.xml?q=MOEX";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            try(InputStream is = new ByteArrayInputStream(response.getBody().getBytes(StandardCharsets.UTF_8))) {
                securitiesXmlImport.importFromFile(is);
                securitiesXmlImport.saveToDb();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/


        return "upload/upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model){
        boolean isSuccessful=false;

        try(InputStream is = file.getInputStream()){
            isSuccessful=securitiesXmlImport.importFromFile(is);
            if(isSuccessful){
                model.addAttribute("recordsCount",securitiesXmlImport.getRecordsCount());
                model.addAttribute("errors",securitiesXmlImport.getErrors());
                securitiesXmlImport.saveToDb();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("isSuccessful",isSuccessful);
        return "upload/upload-result";
    }
}
