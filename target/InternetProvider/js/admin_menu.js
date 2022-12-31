function submit_page(page){
	document.getElementById("pageNumber").value=page
}

function submit_modal(first_name, last_name, login){
	document.getElementById("submit_modal_user_full_name").innerText=first_name.concat(" ", last_name)
	document.getElementById("submit_modal_user_login").innerText=login
}

function change_balance_modal(login, balance) {
	console.log(login)
	console.log(balance)
	document.getElementById("current_user_balance").innerText=balance
	document.getElementById("change_balance_modal_user_login").innerText=login
}