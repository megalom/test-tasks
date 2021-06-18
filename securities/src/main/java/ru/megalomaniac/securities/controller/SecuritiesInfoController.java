package ru.megalomaniac.securities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.service.SecuritiesInfoService;

import java.util.List;

@Controller
@RequestMapping("/securities")
public class SecuritiesInfoController {
    @Autowired
    SecuritiesInfoService securitiesInfoService;

    @GetMapping()
    public String index(Model model){
        List<SecuritiesInfo> securitiesInfos= securitiesInfoService.findAll();
        model.addAttribute("securitiesInfos",securitiesInfos);
        return "/securities/index";
    }

}
