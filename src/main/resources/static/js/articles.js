document.addEventListener("DOMContentLoaded", function() {
    let achats = document.getElementById("achats");
    let ventes = document.getElementById("ventes");
    let listAchats = document.getElementById("list-filtre-achats");
	let listVentes = document.getElementById("list-filtre-ventes");

	if (achats != null) {
		achats.addEventListener("change", function() {
		    if (achats.checked) {
				listAchats.disabled=false;
		        listVentes.disabled=true;
		    }
		});
	}
	if (ventes != null) {
		ventes.addEventListener("change", function() {
		    if (ventes.checked) {
		        listAchats.disabled=true;
		        listVentes.disabled=false;
		    }
		});
	}


});

 
     
