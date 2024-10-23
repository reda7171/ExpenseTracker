package com.et.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.et.expensetracker.models.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	
	
	 

}
