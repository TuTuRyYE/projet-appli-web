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
	$("#ShowMessage").empty();
	$("#Main").load("CreationCompte.html", function() {
		$("#register").click(function() {
			user = {};
			user.pseudo=$("#user-name").val();
			user.email=$("#user-email").val();
			user.mdp=$("#user-pass").val();
			ajaxPost("rest/adduser", user, isJSON=true, function(reponse) {
				console.log(reponse);
				loadConnexion();
				}
			);
		});
	});
}

function invokePost(url, data, successMsg, failureMsg) {
	jQuery.ajax({
	    url: url,
	    type: "POST",
	    data: JSON.stringify(data),
	    dataType: "json",
	    contentType: "application/json; charset=utf-8",
	    success: function (response) {
	    	$("#ShowMessage").text(failureMsg);
	    },
	    error: function (response) {
	    	$("#ShowMessage").text(failureMsg);
	    }
	});
}

function invokeGet(url, failureMsg, responseHandler) {
	jQuery.ajax({
	    url: url,
	    type: "GET",
	    success: responseHandler,
	    error: function (response) {
	    	$("#ShowMessage").text(failureMsg);
	    }
	});
}
//Exécute un appel AJAX POST
//Prend en paramètres l'URL cible, la donnée à envoyer et la fonction callback appelée en cas de succès
//Le paramètre isJson permet d'indiquer si l'envoi concerne des données JSON
function ajaxPost(url, data, isJSON, callback) {
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
     req.setRequestHeader("Content-Type", "application/json");
     // Transforme la donnée du format JSON vers le format texte avant l'envoi
     data = JSON.stringify(data);
 }
 req.send(data);
}
