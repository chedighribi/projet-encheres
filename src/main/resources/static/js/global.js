window.onload = () => {
	var maintenant = new Date();
	var jour='0' + maintenant.getDate();
	var mois='0' + (maintenant.getMonth()+1);
	var an=maintenant.getFullYear();
	spanTxt = document.getElementById('dateDuJour');
	console.log("date " + spanTxt + " => " + maintenant);
	if (spanTxt != null)
		spanTxt.textContent = spanTxt.textContent + " " + jour.slice(-2) + "/" + mois.slice(-2) + "/" + an;

	btnRetour = document.getElementById("btnRetour");
	console.log("bouton retour " + btnRetour);
	if (btnRetour != null)
		btnRetour.addEventListener("click", () => {history.back();});
}