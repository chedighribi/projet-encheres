window.onload = init;

function confirmMotDePasse() {

	console.log("profil.js confirmPassword");
	
   	var mdp = document.getElementById("inputMotdDePasse").value;
   	var confirm_mdp = document.getElementById("inputConfirmation").value;
   	//if (mdp.trim().length > 0)
	console.log(mdp + " ==/!= "+  confirm_mdp);
	if (mdp != confirm_mdp) {
		console.log("profil.js confirmPassword faux");
		document.getElementById('confirmationMdpErreur').classList.remove("d-none");
		document.getElementById('bouton-soumettre').disabled = true;
	} else {
		console.log("profil.js confirmPassword bon");
     	document.getElementById('confirmationMdpErreur').classList.add("d-none");
		document.getElementById('bouton-soumettre').disabled = false;
  	}
}


function init(){
	console.log("profil.js init");
	document.getElementById('confirmationMdpErreur').classList.add("d-none");
	document.getElementById('bouton-soumettre').disabled = true;
	
	document.getElementById('inputMotdDePasse').addEventListener('change', () => {
		document.getElementById('confirmationMdpErreur').classList.remove("d-none");
	});
	document.getElementById('inputConfirmation').addEventListener('change', () => confirmMotDePasse());
	document.getElementById('inputConfirmation').addEventListener('keyup', () => confirmMotDePasse());
	//document.getElementById('profil-form').addEventListener('submit', () => confirmMotDePasse());
}