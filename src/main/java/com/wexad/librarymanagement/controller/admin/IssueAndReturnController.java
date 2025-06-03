package com.wexad.librarymanagement.controller.admin;

import com.wexad.librarymanagement.service.LoanService;
import com.wexad.librarymanagement.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class IssueAndReturnController {

    private final ReservationService reservationService;
    private final LoanService loanService;

    public IssueAndReturnController(ReservationService reservationService, LoanService loanService) {
        this.reservationService = reservationService;
        this.loanService = loanService;
    }

    @PostMapping("/issue/confirm")
    public String issueConfirm(@RequestParam("reservationId") Integer reservationId) {
        reservationService.issue(reservationId);
        return "redirect:/admin/issue";
    }

    @PostMapping("/issue/cancel")
    public String issueCancel(@RequestParam("reservationId") Integer reservationId) {
        reservationService.cancel(reservationId);
        return "redirect:/admin/issue";
    }

    @GetMapping("/issue")
    public String issuePage(Model model) {
        model.addAttribute("reservations", reservationService.getReservedBooks()); // status = reserved
        return "admin/issue";
    }

    @GetMapping("/return")
    public String returnPage(Model model) {
        model.addAttribute("loans", loanService.getActiveLoans()); // return_date is null or not yet returned
        return "admin/return";
    }
    @PostMapping("/return/confirm")
    public String confirmReturn(@RequestParam("loanId") int loanId) {
        loanService.returnBook(loanId);
        return "redirect:/admin/return";
    }
}