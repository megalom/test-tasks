package ru.megalomaniac.securities.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.service.SecuritiesInfoService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SecuritiesRestController {
    @Autowired
    private SecuritiesInfoService securitiesInfoService;

    @GetMapping(value = "/securities",produces=MediaType.APPLICATION_XML_VALUE)
    public List<SecuritiesInfo> findAll(){
        return securitiesInfoService.findAll();
    }

    @GetMapping(value = "/securities/{id}",produces= MediaType.APPLICATION_XML_VALUE)
    public String findAll(@PathVariable(name="id") long id){
        XmlMapper xmlMapper = new XmlMapper();
        String xml = "";
        try {
            xml = xmlMapper.writeValueAsString(securitiesInfoService.findById(id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return xml;
    }
}
