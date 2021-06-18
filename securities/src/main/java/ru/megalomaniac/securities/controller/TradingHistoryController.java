package ru.megalomaniac.securities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.model.TradingHistory;
import ru.megalomaniac.securities.service.TradingHistoryService;

import java.util.List;

@Controller
@RequestMapping("/history")
public class TradingHistoryController {

    @Autowired
    TradingHistoryService tradingHistoryService;

    @GetMapping()
    public String index(Model model){
        List<TradingHistory> tradingHistories= tradingHistoryService.findAll();
        model.addAttribute("tradingHistories",tradingHistories);
        return "/history/index";
    }
}
