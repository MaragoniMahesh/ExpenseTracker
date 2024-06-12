

    let expenses = [];


    function fetchExpenses() {
    fetch('http://localhost:8080/expenses/Expenses')
        .then(response => response.json())
        .then(data => {
            expenses = data;
            renderExpenses();
        })
        .catch(error => console.error('Error fetching expenses:', error));
}


    function addExpense() {
    const nameInput = document.getElementById('name');
    const dateInput = document.getElementById('date');
    const amountInput = document.getElementById('amount');

    const expenseValue = nameInput.value.trim();
    const dateValue = dateInput.value; // Format the date
    const amountValue = parseFloat(amountInput.value);

    if (expenseValue !== '' && dateValue !== 'Invalid Date' && !isNaN(amountValue)) {
    const newExpense = {
    name: expenseValue,
    date: dateValue,
    amount: amountValue
};

    fetch('http://localhost:8080/expenses/Expense', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify(newExpense),
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Failed to add expense');
}
    return response.json();
})
    .then(data => {
    console.log('Expense added successfully:', data);
    expenses.push(data); // Assuming the backend returns the added expense with an ID
    renderExpenses();
    nameInput.value = '';
    dateInput.value = '';
    amountInput.value = '';
})
    .catch(error => {
    console.error('Error adding expense:', error);
});

    nameInput.value = '';
    dateInput.value = '';
    amountInput.value = '';
}
}




    function deleteExpense(id) {
    fetch(`http://localhost:8080/expenses/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete expense');
            }
            expenses = expenses.filter(expense => expense.id !== id);
            renderExpenses();
        })
        .catch(error => console.error('Error deleting expense:', error));
}


    function editExpense(id, newName, newDate, newAmount) {
    fetch(`http://localhost:8080/expenses/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: newName,
            date: newDate,
            amount: newAmount
        }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to edit expense');
            }
            const index = expenses.findIndex(expense => expense.id === id);
            if (index !== -1) {
                expenses[index].name = newName;
                expenses[index].date = newDate;
                expenses[index].amount = newAmount;
                renderExpenses();
            }
        })
        .catch(error => console.error('Error editing expense:', error));
}


    function renderExpenses() {
    const expenseList = document.getElementById('expense-list');
    expenseList.innerHTML = '';
    let totalAmount = 0;
    expenses.forEach(expense => {
    const li = document.createElement('li');
    li.innerHTML = `<span>${expense.name} - ${expense.date} - ${expense.amount}</span>`;
    li.className = 'expense-item';

    const deleteBtn = document.createElement('button');
    deleteBtn.textContent = 'Delete';
    deleteBtn.className = 'btn delete-btn';
    deleteBtn.onclick = function() {
    deleteExpense(expense.id);
};
    li.appendChild(deleteBtn);

    const editBtn = document.createElement('button');
    editBtn.textContent = 'Edit';
    editBtn.className = 'btn edit-btn';
    editBtn.onclick = function() {
    const newName = prompt('Enter new name:', expense.name);
    const newDate = prompt('Enter new date:', expense.date);
    const newAmount = prompt('Enter new amount:', expense.amount);
    if (newName !== null && newDate !== null && newAmount !== null) {
    editExpense(expense.id, newName, newDate, parseFloat(newAmount));
}
};
    li.appendChild(editBtn);

    expenseList.appendChild(li);
    totalAmount += expense.amount;
});
    document.getElementById('total').textContent = `Total: ${totalAmount.toFixed(2)}`;
}

    // Fetch expenses when the page loads
    fetchExpenses();
