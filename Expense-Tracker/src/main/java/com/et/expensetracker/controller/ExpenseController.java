package com.et.expensetracker.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.et.expensetracker.models.Expense;
import com.et.expensetracker.repository.ExpenseRepository;
import com.et.expensetracker.service.ExpenseService;

import ch.qos.logback.core.model.Model;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class ExpenseController {
	
	
	@Autowired
	private ExpenseService expenseService;
	
	
	//get all expense
	@GetMapping("/")
	public String viewHomePage(org.springframework.ui.Model model) {
		List<Expense> expenses = expenseService.getAllExpenses();
		BigDecimal totalAmount = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
	
		model.addAttribute("expenses", expenses);
		model.addAttribute("totalAmount", totalAmount);
		
		return "index";
		
	}
	
	
	
	@GetMapping("/addExpense")
	public String showAddExpensePage(org.springframework.ui.Model model) {
		
		Expense expense = new Expense();
		model.addAttribute("expense",expense);
				
		return "add-expense";
	}
	
	
	@PostMapping("/saveExpense")
	public String saveExpense(@ModelAttribute("expense") Expense expense, org.springframework.ui.Model model) {
		
		expenseService.saveExpense(expense);
	
		return "redirect:/";
	}
	
	@GetMapping("editExpense/{id}")
	public String showUpdateExpensePage(@PathVariable("id") long id ,org.springframework.ui.Model model) {
		
		Expense expense =  expenseService.getExpenseById(id);
		model.addAttribute("expense", expense);
		
		return "update-expense";
	}
	
	
	@PostMapping("/updateExpense/{id}")
	public String updateExpense(@PathVariable("id") long id,@ModelAttribute("expense") Expense expense ) {
		
		Expense existingExpense =  expenseService.getExpenseById(id);
		existingExpense.setDescription(expense.getDescription());
		existingExpense.setAmount(expense.getAmount());
		expenseService.saveExpense(existingExpense);
		
		return "redirect:/";
	}
	
	@Transactional
	@GetMapping("/deleteExpense/{id}")
	public String deleteExpense(@PathVariable("id") long id) {
		
		expenseService.deleteExpenseById(id);
		
		return "redirect:/";
	}
	
	
	
	
	
	
	

	
}
