function select_service(id){
	if(document.getElementById("service_id").value==id) document.getElementById("service_id").value=0
	else document.getElementById("service_id").value=id
}

function change_sorting_field(sort_field){
	document.getElementById("sorting_field").value=sort_field.toLowerCase()
}
function change_sorting_order(){
	document.getElementById("sorting_order").value = document.getElementById("sorting_order").value == "ASC"?"DESC":"ASC"
	
}
function submit_page(page){
	document.getElementById("page_number").value=page
}

function confirm_tariff_selection(price,name,id){
	document.getElementById('tariff_name').innerHTML=name
	document.getElementById('tariff_price').innerHTML=price
	document.getElementById('tariff_id').value=id
}

function confirm_tariff_remove(name,id) {
	document.getElementById('tariff_name').innerHTML=name
	document.getElementById('tariff_id').value=id
}

function edit_tariff(name, price, serviceId, description, id){
	document.getElementById("edit_tariff_name").value=name
	document.getElementById("edit_tariff_rate").value=price
	document.getElementById("edit_tariff_description").value=description
	document.getElementById("edit_tariff_service_id").value=serviceId
	document.getElementById("edit_tariff_id").value=id
}
