function submit_page(page){
	console.log(page)
	document.getElementById("pageNumber").value=page
}
function confirm_tariff_remove(name,id){
	document.getElementById('tariff_name').innerHTML=name
	document.getElementById('tariff_id').value=id
}