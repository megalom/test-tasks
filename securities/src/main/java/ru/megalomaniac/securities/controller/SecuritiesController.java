package ru.megalomaniac.securities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.megalomaniac.securities.xml.SecuritiesXmlImport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
}
