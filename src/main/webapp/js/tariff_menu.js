function select_service(name){
	if(document.getElementById("service_name").value==name) document.getElementById("service_name").value="ALL"
	else document.getElementById("service_name").value=name
}

function change_sorting_field(sort_field){
	if(sort_field === 'Назвою') sort_field = 'name';
	else if(sort_field === 'Ціною') sort_field = 'rate';
	document.getElementById("sorting_field").value=sort_field.toLowerCase()
}
function change_sorting_order(){
	document.getElementById("sorting_order").value = document.getElementById("sorting_order").value == "ASC"?"DESC":"ASC"
	
}
function submit_page(page){
	document.getElementById("page_number").value=page
}

function confirm_tariff_selection(price,name,id,days){
	document.getElementById('tariff_name').innerHTML=name
	document.getElementById('tariff_rate').innerHTML=price
	document.getElementById('tariff_id').value=id
	document.getElementById('tariff_days').innerHTML=days
}

function confirm_tariff_remove(name,id) {
	document.getElementById('tariff_name').innerHTML=name
	document.getElementById('tariff_id').value=id
}

function edit_tariff(name, rate, service, description, payment_period, id){
	document.getElementById("edit_tariff_name").value=name
	document.getElementById("edit_tariff_rate").value=rate
	document.getElementById("edit_tariff_description").value=description
	document.getElementById("edit_tariff_service").value=service
	document.getElementById("payment_period").value=payment_period
	document.getElementById("payment_period_span").innerText=payment_period
	document.getElementById("edit_tariff_id").value=id
}
