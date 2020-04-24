/**
 * 
 */

$(document).ready(function() {
	loadConnexion();
});

function loadConnexion() {
	$("#Main").load("Connexion.html", function() {
		$("#login").click(function() {
			//à faire
		});
		$("#signup").click(function() {
			loadSignup();
		});
	});
}

function loadSignup() {
	$("#Main").empty();
	$("#Main").load("CreationCompte.html", function() {
		
	});
}