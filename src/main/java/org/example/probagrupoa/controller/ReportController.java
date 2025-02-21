package org.example.probagrupoa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @GetMapping("/pdf/account.report_invoice/{id}")
    public void reportInvoice(@PathVariable("id") Integer id) {
        System.out.println("Report Invoice del id: " + id);
    }
}
