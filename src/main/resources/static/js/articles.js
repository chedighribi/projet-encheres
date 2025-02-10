document.addEventListener("DOMContentLoaded", function() {
    let achats = document.getElementById("achats");
    let ventes = document.getElementById("ventes");
    let listAchats = document.getElementById("list-filtre-achats");
	let listVentes = document.getElementById("list-filtre-ventes");

	achats.addEventListener("change", function() {
    if (achats.checked) {
		listAchats.disabled=false;
        listVentes.disabled=true;
    }
});

ventes.addEventListener("change", function() {
    if (ventes.checked) {
        listAchats.disabled=true;
        listVentes.disabled=false;
    }
});
});