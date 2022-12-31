function submit_page(page){
	document.getElementById("pageNumber").value=page
}

function submit_modal(first_name, last_name, login, id){
	document.getElementById("submit_modal_user_full_name").innerText=first_name.concat(" ", last_name)
	document.getElementById("submit_modal_user_login").innerText=login
	document.getElementById("remove_user_id").value=id
}

function change_balance_modal(login, balance,id) {
	document.getElementById("current_user_balance").innerText=balance
	document.getElementById("change_balance_modal_user_login").innerText=login
	document.getElementById("change_balance_user_id").value=id
}