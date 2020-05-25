/**
 * 
 */

$(document).ready(function() {
	loadConnexion("","");
});

function loadConnexion(username, password) {
	
	$("#Main").load("Connexion.html", function() {
		$("#user-name").val(username);
		$("#user-pass").val(password);
		$("#login").click(function() {
			credentials = {};
			credentials.username = $("#user-name").val();
			credentials.password = $("#user-pass").val();
			console.log(credentials);
			var b64 = btoa(credentials.username+":"+credentials.password);
			console.log(b64);
			ajaxPost("rest/secured/message", credentials, b64, isJSON=true, function(response) {
				console.log(response);
				}
			);
		});
		$("#signup").click(function() {
			loadSignup();
		});
	});
}

function loadSignup() {
	$("#Main").load("CreationCompte.html", function() {
		$("#register").click(function() {
			user = {};
			user.username=$("#user-name").val().trim();
			user.email=$("#user-email").val().trim();
			user.password=$("#user-pass").val().trim();
			ajaxPost("rest/adduser", user, "", isJSON=true, function(response) {
				console.log(response);
				if(response == "emailAlreadyUsed") {
					$("#ShowMessage").text("Un utilisateur possède déjà cet email")
				} else if(response == "pseudoAlreadyUsed") {
					$("#ShowMessage").text("Un utilisateur possède déjà ce pseudo")
				} else if(response == "badInput") {
					$("#ShowMessage").text("Veuillez remplir correctement les différents champs")
				} else {
					loadConnexion(user.name, user.password);
					$("#ShowMessage").text("Votre compte a été crée");
				}
				}
			);
		});
	});
}

//Exécute un appel AJAX GET
//Prend en paramètres l'URL cible et la fonction callback appelée en cas de succès
function ajaxGet(url, callback) {
 var req = new XMLHttpRequest();
 req.open("GET", url);
 req.addEventListener("load", function () {
     if (req.status >= 200 && req.status < 400) {
         // Appelle la fonction callback en lui passant la réponse de la requête
         callback(req.responseText);
     } else {
         console.error(req.status + " " + req.statusText + " " + url);
     }
 });
 req.addEventListener("error", function () {
     console.error("Erreur réseau avec l'URL " + url);
 });
 req.send(null);
}

//Exécute un appel AJAX POST
//Prend en paramètres l'URL cible, la donnée à envoyer et la fonction callback appelée en cas de succès
//Le paramètre isJson permet d'indiquer si l'envoi concerne des données JSON
function ajaxPost(url, data, authorizationToken, isJSON, callback) {
 var req = new XMLHttpRequest();
 req.open("POST", url);
 req.addEventListener("load", function () {
     if (req.status >= 200 && req.status < 400) {
         // Appelle la fonction callback en lui passant la réponse de la requête
         callback(req.responseText);
     } else {
         console.error(req.status + " " + req.statusText + " " + url);
     }
 });
 req.addEventListener("error", function () {
     console.error("Erreur réseau avec l'URL " + url);
 });
 if (isJSON) {
     // Définit le contenu de la requête comme étant du JSON
	 req.setRequestHeader("Authorization", "Basic " + authorizationToken);
     req.setRequestHeader("Content-Type", "application/json");
     // Transforme la donnée du format JSON vers le format texte avant l'envoi
     data = JSON.stringify(data);
     console.log(data);
 }
 req.send(data);
}
