/**
 * 
 */
var b64_credentials;

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
			b64_credentials = btoa(credentials.username+":"+credentials.password);
			console.log(b64_credentials);
			ajaxGet("rest/secured/login", b64_credentials, function(response) {
				console.log(response);
				if(response == "Connected") {
					loadProfil(credentials.username);
				}
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
					loadConnexion(user.username, user.password);
					$("#ShowMessage").text("Votre compte a été crée");
				}
				}
			);
		});
	});
}

function loadNavbar() {
	$("#Navbar").load("Navbar.html", function() {
		$("#search").val("");
		$("#searchButton").click(function() {
			filmName = $("#search").val().trim();
			console.log(filmName);
			ajaxGet("http://www.omdbapi.com/?apikey=2a61fcd&t="+filmName, "", function(response) {
				console.log(response);
				if(response.response != "False") {
					loadFilm(response);
				}
				}
			);
		});
		$("#signup").click(function() {
			loadSignup();
		});
	});
}

function loadProfil(username) {
	$("#ShowMessage").text("");
	loadNavbar("");
	$("#Footer").load("Footer.html");
	$("#Main").load("page_profil.html", function() {
		
				
		});
}

function loadFilm(infosFilm) {
	var infosJSON = JSON.parse(infosFilm);
	$("#ShowMessage").text("");
	$("#Main").load("page_film.html", function() {
		$("#Poster").attr("src", infosJSON.Poster);
		$("#Title").text(infosJSON.Title);
		$("#Year").text("Année : "+infosJSON.Year);	
		});
}


//Exécute un appel AJAX GET
//Prend en paramètres l'URL cible et la fonction callback appelée en cas de succès
function ajaxGet(url, authorizationToken, callback) {
 var req = new XMLHttpRequest();
 req.open("GET", url);
 if(authorizationToken != "") {
	 req.setRequestHeader("Authorization", "Basic " + authorizationToken);
 }
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
