window.onload = () => {
	var maintenant = new Date();
	var jour='0' + maintenant.getDate();
	var mois='0' + (maintenant.getMonth()+1);
	var an=maintenant.getFullYear();
	spanTxt = document.getElementById('dateDuJour');
	if (spanTxt != null)
		spanTxt.textContent = spanTxt.textContent + " " + jour.slice(-2) + "/" + mois.slice(-2) + "/" + an;

	btnRetour = document.getElementById("btnRetour");
	if (btnRetour != null)
		btnRetour.addEventListener("click", () => {history.back();});
	
	init();
}

function confirmMotDePasse() {
	var mdp = document.getElementById("inputMotdDePasse").value;
   	var confirm_mdp = document.getElementById("inputConfirmation").value;
	if (mdp != confirm_mdp) {
		document.getElementById('confirmationMdpErreur').classList.remove("d-none");
		document.getElementById('bouton-soumettre').disabled = true;
	} else {
     	document.getElementById('confirmationMdpErreur').classList.add("d-none");
		document.getElementById('bouton-soumettre').disabled = false;
  	}
}

function init(){
	inputMDP = document.getElementById('inputMotdDePasse');
	if (inputMDP != null) {
		document.getElementById('confirmationMdpErreur').classList.add("d-none");
		document.getElementById('bouton-soumettre').disabled = true;
		
		inputMDP.addEventListener('change', () => {
			document.getElementById('confirmationMdpErreur').classList.remove("d-none");
		});
		document.getElementById('inputConfirmation').addEventListener('change', () => confirmMotDePasse());
		document.getElementById('inputConfirmation').addEventListener('keyup', () => confirmMotDePasse());
	}
}