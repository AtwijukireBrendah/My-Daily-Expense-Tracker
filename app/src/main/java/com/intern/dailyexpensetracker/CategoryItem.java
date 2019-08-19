package com.intern.dailyexpensetracker;

public class CategoryItem {
    private String expense;
    private String category;
    private String amount;
    private String date;
    public CategoryItem(String expense, String category, String amount, String date){
        this.expense=expense;
        this.category=category;
        this.amount=amount;
        this.date=date;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
